package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.advertising.Advertising;
import org.telegrambot.convertio.core.repositories.AdvertisingRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdvertisingService implements Service<Advertising, Long> {
    @Resource
    private final AdvertisingRepository advertisingRepository;

    @Override
    public Advertising create(Advertising advertising) {
        return advertisingRepository.save(advertising);
    }

    @Override
    public Advertising update(Advertising advertising) {
        return advertisingRepository.save(advertising);
    }

    @Override
    public Advertising read(Long id) throws ServiceException {
        Advertising advertising = advertisingRepository.findById(id).orElse(null);
        if (advertising == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return advertising;
    }

    @Override
    public void delete(Long id) {
        advertisingRepository.deleteById(id);
    }

    @Override
    public List<Advertising> getAll() {
        return advertisingRepository.findAll();
    }

    @Override
    public long count() {
        return advertisingRepository.count();
    }

}
