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
public class ReferralUserReversePopulator implements Populator<ReferralUserData, ReferralUser> {
    @Resource
    @Qualifier("userReverseConverter")
    private final Converter<UserData, User> userReverseConverter;
    @Resource
    @Qualifier("referralReverseConverter")
    private final Converter<ReferralData, Referral> referralReverseConverter;

    @Override
    public void populate(ReferralUserData source, ReferralUser target) throws ConversionException {
        target.setId(source.getId());
        target.setUser(userReverseConverter.convert(source.getUser()));
        target.setReferral(referralReverseConverter.convert(source.getReferral()));
    }
}
