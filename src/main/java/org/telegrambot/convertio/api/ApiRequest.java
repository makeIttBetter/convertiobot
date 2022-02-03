package org.telegrambot.convertio.api;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.api.annotations.ApiParam;
import org.telegrambot.convertio.api.annotations.JsonParam;
import org.telegrambot.convertio.api.anymoney.enums.EnumParam;
import org.telegrambot.convertio.exceptions.apis.ApiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public abstract class ApiRequest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private String message;

    /**
     * Forms JSONObject from ApiRequest Api params or it's heirs' Api params
     *
     * @return JSONObject request
     * @throws ApiException if something went wrong
     */
    abstract public JSONObject toJson() throws ApiException;

    /**
     * Forms JSONObject from ApiRequest params or it's heirs' params
     *
     * @return JSONObject params
     * @throws ApiException if something went wrong
     */
    public JSONObject getParams() throws ApiException {
        try {
            if (isCurrentObjValid()) {
                Method[] declaredMethods = this.getClass().getMethods();
                JSONObject jsonObject = new JSONObject();
                // get only annotated methods
                List<Method> getters = Arrays.stream(declaredMethods)
                        .filter(m -> AnnotationUtils.findAnnotation(m, JsonParam.class) != null)
                        .collect(Collectors.toList());
                // fulfill Json
                for (Method method : getters) {
                    if (method.invoke(this) != null) {
                        setAttribute(jsonObject, method);
                    }
                }
                log.info("Params formed: " + jsonObject);
                return jsonObject;
            } else {
                log.error("Request has invalid parameters: " + getMessage());
                throw new ApiException(getMessage());
            }
        } catch (Exception e) {
            log.error("Error while to Json transformation" + e);
            throw new ApiException("Error while to Json transformation", e);
        }
    }

    /**
     * Puts attribute to Json object
     *
     * @param jsonObject json object in which method will put values
     * @param method     method that returns value to put in json object
     * @throws InvocationTargetException reflection api exception while executing method
     * @throws IllegalAccessException    reflection api exception while access method
     */
    private void setAttribute(JSONObject jsonObject, Method method)
            throws InvocationTargetException, IllegalAccessException {
        String key = Objects.requireNonNull
                (AnnotationUtils.findAnnotation(method, JsonParam.class)).value();
        Object returnObj = method.invoke(this);
        Class<?> clazz = method.getReturnType();

        String value;
        if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
            value = String.valueOf(returnObj);
            boolean param = Boolean.parseBoolean(value);
            jsonObject.put(key, param);
            log.info("Put Boolean value: " + value);
        } else if (EnumParam.class.isAssignableFrom(clazz)) {
            EnumParam enumParam = (EnumParam) returnObj;
            value = enumParam.getName();
            log.info("Put enum value: " + value);
        } else {
            value = String.valueOf(returnObj);
            log.info("Put not an enum: " + value);
        }
        jsonObject.put(key, value);
    }


    /**
     * Validates current object before creating json object
     *
     * @return if curr object valid - true, else - false
     */
    private Boolean isCurrentObjValid() {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ApiRequest>> violations
                = validator.validate(this);
        Boolean isValid = violations.isEmpty();
        if (!isValid) {
            setMessage("Errors found while validating request: "
                    + violations);
            log.error(getMessage());
        }
        return isValid;
    }

    private List<Method> getAllApiRequestAnnotatedMethods() {
        List<Method> methods = new ArrayList<>();

        Class<?> clazz = this.getClass();
        while (clazz != null) {
            methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
            // Print fields.
            clazz = clazz.getSuperclass();
        }
        methods = methods.stream()
                .filter(m -> AnnotationUtils.findAnnotation(m, ApiParam.class) != null)
                .collect(Collectors.toList());

        return methods;
    }

    private String getMessage() {
        return this.message;
    }

    // getters / setters
    private void setMessage(String message) {
        this.message = message;
    }
}
