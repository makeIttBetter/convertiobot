package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.telegrambot.convertio.core.model.user.Request;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.repositories.RequestRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Transactional
@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RequestService implements Service<Request, Long> {
    @Resource
    private final RequestRepository requestRepository;

    @Override
    public Request create(Request request) throws ServiceException {
        try {
            return requestRepository.save(request);
        } catch (DataIntegrityViolationException e) {
            log.error("Cannot save request into the DB. Duplicate entry.", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Request update(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request read(Long id) throws ServiceException {
        Request request = requestRepository.findById(id).orElse(null);
        if (request == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return request;
    }

    public void deleteLastByUser(User user) {
        Request request = requestRepository.findLastByUser(user);
        delete(request.getId());
    }


    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.SERIALIZABLE)
    public Request getLastAndDeleteByUser(User user) throws ServiceException {
        List<Request> requests = requestRepository.findTwoLastByUserOrderByIdDesc(user);
        if (requests == null || requests.isEmpty()) {
            throw new ObjectNotFoundException("Entity not found");
        } else if (requests.size() > 1) {
            // getting values
            Request last = requests.get(0);
            Request penultimate = requests.get(1);
            // deleting values
            delete(last.getId());
            delete(penultimate.getId());
            return penultimate;
        } else {
            // getting value
            Request last = requests.get(0);
            // deleting value
            delete(last.getId());
            return last;
        }
    }

    public void deleteAllByUser(User user) {
        requestRepository.deleteAllByUser(user);
    }

    @Override
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public List<Request> getAll() {
        return requestRepository.findAll();
    }

    @Override
    public long count() {
        return requestRepository.count();
    }
}
