package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.advertising.Advertising;
import org.telegrambot.convertio.core.services.AdvertisingService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.advertising.AdvertisingData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdvertisingFacade implements Facade<AdvertisingData, Long> {
    @Resource
    @Qualifier("advertisingConverter")
    private final Converter<Advertising, AdvertisingData> converter;
    @Resource
    @Qualifier("advertisingReverseConverter")
    private final Converter<AdvertisingData, Advertising> reverseConverter;
    @Resource
    private final AdvertisingService advertisingService;

    @Override
    public AdvertisingData create(AdvertisingData dataObject) throws FacadeException {
        try {
            Advertising model = reverseConverter.convert(dataObject);
            advertisingService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public AdvertisingData update(AdvertisingData dataObject) throws FacadeException {
        try {
            Advertising model = reverseConverter.convert(dataObject);
            advertisingService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public AdvertisingData read(Long id) throws FacadeException {
        try {
            return converter.convert(advertisingService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            advertisingService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<AdvertisingData> getAll() {
        return converter.convertAll(advertisingService.getAll());
    }

    @Override
    public long count() {
        return advertisingService.count();
    }

}
