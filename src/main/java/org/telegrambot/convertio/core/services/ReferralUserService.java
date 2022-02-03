package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.advertising.Referral;
import org.telegrambot.convertio.core.model.advertising.ReferralUser;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.repositories.ReferralUserRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReferralUserService implements Service<ReferralUser, Long> {
    @Resource
    private final ReferralUserRepository referralUserRepository;

    @Override
    public ReferralUser create(ReferralUser referralUser) {
        return referralUserRepository.save(referralUser);
    }

    @Override
    public ReferralUser update(ReferralUser referralUser) {
        return referralUserRepository.save(referralUser);
    }

    @Override
    public ReferralUser read(Long id) throws ServiceException {
        ReferralUser referralUser = referralUserRepository.findById(id).orElse(null);
        if (referralUser == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return referralUser;
    }

    @Override
    public void delete(Long id) {
        referralUserRepository.deleteById(id);
    }

    @Override
    public List<ReferralUser> getAll() {
        return referralUserRepository.findAll();
    }

    @Override
    public long count() {
        return referralUserRepository.count();
    }

    public List<ReferralUser> findAllByReferral(Referral referral) {
        return referralUserRepository.findAllByReferral(referral);
    }

    public List<ReferralUser> findAllByUser(User user) {
        return referralUserRepository.findAllByUser(user);
    }

    public ReferralUser findFirstByUserAndReferral(User user, Referral referral) {
        return referralUserRepository.findFirstByUserAndReferral(user, referral);
    }
}
