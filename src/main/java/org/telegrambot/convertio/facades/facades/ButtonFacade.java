package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.advertising.Button;
import org.telegrambot.convertio.core.services.ButtonService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.advertising.ButtonData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ButtonFacade implements Facade<ButtonData, Long> {
    @Resource
    @Qualifier("buttonConverter")
    private final Converter<Button, ButtonData> converter;
    @Resource
    @Qualifier("buttonReverseConverter")
    private final Converter<ButtonData, Button> reverseConverter;
    @Resource
    private final ButtonService buttonService;

    @Override
    public ButtonData create(ButtonData dataObject) throws FacadeException {
        try {
            Button model = reverseConverter.convert(dataObject);
            buttonService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public ButtonData update(ButtonData dataObject) throws FacadeException {
        try {
            Button model = reverseConverter.convert(dataObject);
            buttonService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public ButtonData read(Long id) throws FacadeException {
        try {
            return converter.convert(buttonService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            buttonService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<ButtonData> getAll() {
        return converter.convertAll(buttonService.getAll());
    }

    @Override
    public long count() {
        return buttonService.count();
    }

    public List<ButtonData> findAllByIds(List<Long> idList) {
        return converter.convertAll(buttonService.findAllByIds(idList));
    }

}