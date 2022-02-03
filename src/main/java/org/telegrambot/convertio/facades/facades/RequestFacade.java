package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Isolation;
import org.telegrambot.convertio.core.model.user.Request;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.services.RequestService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.user.RequestData;
import org.telegrambot.convertio.facades.data.user.UserData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RequestFacade implements Facade<RequestData, Long> {
    @Resource
    @Qualifier("requestConverter")
    private final Converter<Request, RequestData> converter;
    @Resource
    @Qualifier("requestReverseConverter")
    private final Converter<RequestData, Request> reverseConverter;
    @Resource
    @Qualifier("userReverseConverter")
    private final Converter<UserData, User> reverseUserConverter;
    @Resource
    private final RequestService requestService;

    @Override
    public RequestData create(RequestData dataObject) throws FacadeException {
        try {
            Request model = reverseConverter.convert(dataObject);
            requestService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public RequestData update(RequestData dataObject) throws FacadeException {
        try {
            Request model = reverseConverter.convert(dataObject);
            requestService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public RequestData read(Long id) throws FacadeException {
        try {
            return converter.convert(requestService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            requestService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<RequestData> getAll() {
        return converter.convertAll(requestService.getAll());
    }

    @Override
    public long count() {
        return requestService.count();
    }

    public void deleteLastByUser(UserData user) {
        User userModel = reverseUserConverter.convert(user);
        requestService.deleteLastByUser(userModel);
    }

    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.SERIALIZABLE)
    public RequestData getLastAndDeleteByUser(UserData user) throws ServiceException {
        User userModel = reverseUserConverter.convert(user);
        Request request = requestService.getLastAndDeleteByUser(userModel);
        return converter.convert(request);
    }

    public void deleteAllByUser(UserData user) {
        User userModel = reverseUserConverter.convert(user);
        requestService.deleteAllByUser(userModel);
    }

}
