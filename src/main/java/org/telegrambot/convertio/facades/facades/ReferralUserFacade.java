package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.advertising.Referral;
import org.telegrambot.convertio.core.model.advertising.ReferralUser;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.services.ReferralUserService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.advertising.ReferralData;
import org.telegrambot.convertio.facades.data.advertising.ReferralUserData;
import org.telegrambot.convertio.facades.data.user.UserData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReferralUserFacade implements Facade<ReferralUserData, Long> {
    @Resource
    @Qualifier("referralUserConverter")
    private final Converter<ReferralUser, ReferralUserData> converter;
    @Resource
    @Qualifier("referralUserReverseConverter")
    private final Converter<ReferralUserData, ReferralUser> reverseConverter;
    @Resource
    @Qualifier("userReverseConverter")
    private final Converter<UserData, User> reverseUserConverter;
    @Resource
    @Qualifier("referralReverseConverter")
    private final Converter<ReferralData, Referral> reverseReferralConverter;
    @Resource
    private final ReferralUserService referralUserService;

    @Override
    public ReferralUserData create(ReferralUserData dataObject) throws FacadeException {
        try {
            ReferralUser model = reverseConverter.convert(dataObject);
            referralUserService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public ReferralUserData update(ReferralUserData dataObject) throws FacadeException {
        try {
            ReferralUser model = reverseConverter.convert(dataObject);
            referralUserService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public ReferralUserData read(Long id) throws FacadeException {
        try {
            return converter.convert(referralUserService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            referralUserService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<ReferralUserData> getAll() {
        return converter.convertAll(referralUserService.getAll());
    }

    @Override
    public long count() {
        return referralUserService.count();
    }

    public List<ReferralUserData> findAllByReferral(ReferralData referral) {
        Referral referralModel = reverseReferralConverter.convert(referral);
        List<ReferralUser> referralUsers = referralUserService.findAllByReferral(referralModel);
        return converter.convertAll(referralUsers);
    }

    public List<ReferralUserData> findAllByUser(UserData user) {
        User userModel = reverseUserConverter.convert(user);
        List<ReferralUser> referralUsers = referralUserService.findAllByUser(userModel);
        return converter.convertAll(referralUsers);
    }

    public ReferralUserData findFirstByUserAndReferral(UserData user, ReferralData referral) {
        User userModel = reverseUserConverter.convert(user);
        Referral referralModel = reverseReferralConverter.convert(referral);
        ReferralUser referralUser = referralUserService.findFirstByUserAndReferral(userModel, referralModel);
        return converter.convert(referralUser);
    }

}
