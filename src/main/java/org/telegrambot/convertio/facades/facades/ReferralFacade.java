package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.advertising.Referral;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.services.ReferralService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.advertising.ReferralData;
import org.telegrambot.convertio.facades.data.user.UserData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReferralFacade implements Facade<ReferralData, Long> {
    @Resource
    @Qualifier("referralConverter")
    private final Converter<Referral, ReferralData> converter;
    @Resource
    @Qualifier("referralReverseConverter")
    private final Converter<ReferralData, Referral> reverseConverter;
    @Resource
    @Qualifier("userReverseConverter")
    private final Converter<UserData, User> reverseUserConverter;
    @Resource
    private final ReferralService referralService;

    @Override
    public ReferralData create(ReferralData dataObject) throws FacadeException {
        try {
            Referral model = reverseConverter.convert(dataObject);
            referralService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public ReferralData update(ReferralData dataObject) throws FacadeException {
        try {
            Referral model = reverseConverter.convert(dataObject);
            referralService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public ReferralData read(Long id) throws FacadeException {
        try {
            return converter.convert(referralService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            referralService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<ReferralData> getAll() {
        return converter.convertAll(referralService.getAll());
    }

    @Override
    public long count() {
        return referralService.count();
    }

    public ReferralData findFirstByPayload(String payload) {
        return converter.convert(referralService.findFirstByPayload(payload));
    }

    public void updateReferralStatByUserAndPayload(UserData user, String payload) {
        User userModel = reverseUserConverter.convert(user);
        referralService.updateReferralStatByUserAndPayload(userModel, payload);
    }

}
