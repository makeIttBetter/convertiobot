package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.advertising.Button;
import org.telegrambot.convertio.core.repositories.ButtonRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ButtonService implements Service<Button, Long> {
    @Resource
    private final ButtonRepository buttonRepository;

    @Override
    public Button create(Button button) {
        return buttonRepository.save(button);
    }

    @Override
    public Button update(Button button) {
        return buttonRepository.save(button);
    }

    @Override
    public Button read(Long id) throws ServiceException {
        Button button = buttonRepository.findById(id).orElse(null);
        if (button == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return button;
    }

    @Override
    public void delete(Long id) {
        buttonRepository.deleteById(id);
    }

    @Override
    public List<Button> getAll() {
        return buttonRepository.findAll();
    }

    @Override
    public long count() {
        return buttonRepository.count();
    }

    public List<Button> findAllByIds(List<Long> idList) {
        return buttonRepository.findAllByIdIn(idList);
    }

}
