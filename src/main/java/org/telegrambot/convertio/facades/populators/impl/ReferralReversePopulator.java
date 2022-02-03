package org.telegrambot.convertio.facades.populators.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.advertising.Referral;
import org.telegrambot.convertio.facades.data.advertising.ReferralData;
import org.telegrambot.convertio.facades.populators.Populator;

@Component
@RequiredArgsConstructor
public class ReferralReversePopulator implements Populator<ReferralData, Referral> {
    @Override
    public void populate(ReferralData source, Referral target) throws ConversionException {
        target.setId(source.getId());
        target.setActive(source.isActive());
        target.setPayload(source.getPayload());
        target.setNewUsers(source.getNewUsers());
        target.setAllClicks(source.getAllClicks());
        target.setUniqueClicks(source.getUniqueClicks());
    }
}
