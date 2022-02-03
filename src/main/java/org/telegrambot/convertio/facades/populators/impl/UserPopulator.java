package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.populators.Populator;

@Component
public class UserPopulator implements Populator<User, UserData> {
    @Override
    public void populate(User source, UserData target) throws ConversionException {
        target.setId(source.getId());
        target.setChatId(source.getChatId());
        target.setName(source.getName());
        target.setUserName(source.getUserName());
        target.setActivityDate(source.getActivityDate());
        target.setRegisterDate(source.getRegisterDate());
        target.setRole(source.getRole());
        target.setLanguage(source.getLanguage());
        target.setAdminBlock(source.isAdminBlock());
        target.setAutoBlock(source.isAutoBlock());
        target.setBotState(source.getBotState());
        target.setSaveHistory(source.isSaveHistory());
        target.setRequestAmount(source.getRequestAmount());
        target.setBlockDate(source.getBlockDate());
        target.setOverloadStartTime(source.getOverloadStartTime());
    }
}
