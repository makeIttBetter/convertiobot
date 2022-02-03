package org.telegrambot.convertio.facades.populators.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.advertising.Referral;
import org.telegrambot.convertio.core.model.advertising.ReferralUser;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.advertising.ReferralData;
import org.telegrambot.convertio.facades.data.advertising.ReferralUserData;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;

@Component
@RequiredArgsConstructor
public class ReferralUserPopulator implements Populator<ReferralUser, ReferralUserData> {
    @Resource
    @Qualifier("userConverter")
    private final Converter<User, UserData> userConverter;
    @Resource
    @Qualifier("referralConverter")
    private final Converter<Referral, ReferralData> referralConverter;

    @Override
    public void populate(ReferralUser source, ReferralUserData target) throws ConversionException {
        target.setId(source.getId());
        target.setUser(userConverter.convert(source.getUser()));
        target.setReferral(referralConverter.convert(source.getReferral()));
    }
}
