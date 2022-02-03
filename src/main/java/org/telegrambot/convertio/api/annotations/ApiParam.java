package org.telegrambot.convertio.api.annotations;

import java.lang.annotation.*;

/**
 * Request to Api request consists of 2 jsons:
 * "main" and "nested (params)" so,
 * this annotation is needed to mark
 * fields or getters of fields that should be
 * injected as a parameters to "main" Json request object
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ApiParam {
    String value();
}
