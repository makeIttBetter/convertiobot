package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.advertising.Referral;
import org.telegrambot.convertio.core.model.advertising.ReferralUser;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.repositories.ReferralRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReferralService implements Service<Referral, Long> {
    @Resource
    private final ReferralRepository referralRepository;
    @Resource
    private final ReferralUserService referralUserService;

    @Override
    public Referral create(Referral referral) {
        return referralRepository.save(referral);
    }

    @Override
    public Referral update(Referral referral) {
        return referralRepository.save(referral);
    }

    @Override
    public Referral read(Long id) throws ServiceException {
        Referral referral = referralRepository.findById(id).orElse(null);
        if (referral == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return referral;
    }

    @Override
    public void delete(Long id) {
        referralRepository.deleteById(id);
    }

    @Override
    public List<Referral> getAll() {
        return referralRepository.findAll();
    }

    @Override
    public long count() {
        return referralRepository.count();
    }

    public Referral findFirstByPayload(String payload) {
        return referralRepository.findFirstByPayload(payload);
    }


    public void updateReferralStatByUserAndPayload(User user, String payload) {
        try {
            Referral referral = findFirstByPayload(payload);
            referral.setAllClicks(referral.getAllClicks() + 1);
            try {
                referralUserService.findFirstByUserAndReferral(user, referral);
            } catch (NoSuchElementException e) {
                ReferralUser referralUser = new ReferralUser(user, referral);
                referralUserService.create(referralUser);
                referral.setUniqueClicks(referral.getUniqueClicks() + 1);
            }
            long currentMillis = System.currentTimeMillis() - 5000;
            if (user.getRegisterDate().toInstant().isAfter(new Date(currentMillis).toInstant())) {
                referral.setNewUsers(referral.getNewUsers() + 1);
            }
            create(referral);
        } catch (NoSuchElementException e) {
            Referral referral = Referral.builder().payload(payload).active(true)
                    .allClicks(0).newUsers(0).uniqueClicks(0).build();
            create(referral);
        }
    }

}
