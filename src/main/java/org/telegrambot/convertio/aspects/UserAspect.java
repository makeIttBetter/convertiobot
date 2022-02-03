package org.telegrambot.convertio.aspects;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.UserFacade;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;
import org.telegrambot.convertio.occ.helpers.TimeHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAspect {
    private final MessageBuilder messageBuilder;
    private final UserFacade userFacade;
    private final TimeHelper timeHelper;
    @Value("${autoBlockTimeHours}")
    @Setter
    private int autoBlockTimeHours;

    /**
     * Mapping for the needed method
     */
    @Pointcut("execution(public java.util.Map<org.telegrambot.convertio.facades.data.user.UserData," +
            " org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO>" +
            " org.telegrambot.convertio.occ.bot.commands.Command.execute(..))")
    private void commandMainMethod() {
    }


    /**
     * Checked if user is blocked.
     * if true, returns message about it to user,
     * if false do nothing
     */
    @Around("commandMainMethod()")
    public Object checkIfUserBlocked(ProceedingJoinPoint pjp) throws Throwable {
        BotContextWsDTO botContext = (BotContextWsDTO) pjp.getArgs()[0];
        Map<UserData, BotResponseWsDTO> botResponseMap = new HashMap<>();

        UserData user = botContext.getUser();
        boolean isUnblocked = false;

        if (user.isAutoBlock()) {
            if (canUserBeUnblocked(user)) {
                // unblocking user
                user.setAutoBlock(false);
                userFacade.update(user);
                isUnblocked = true;
            }
            BotResponseWsDTO botResponse = new BotResponseWsDTO();
            // sending message about auto block
            String timeLeft = getTimeLeftView(user);

            SendMessage sendMessage = getUserIsAutoBlockMessage(user, timeLeft);
            botResponse.add(sendMessage);

            botResponseMap.put(user, botResponse);
        } else if (user.isAdminBlock()) {
            BotResponseWsDTO botResponse = new BotResponseWsDTO();
            // sending message about admin block
            SendMessage sendMessage = getUserIsAdminBlockMessage(user);
            botResponse.add(sendMessage);

            botResponseMap.put(user, botResponse);
        } else {
            isUnblocked = true;
        }
        if (isUnblocked) {
            botResponseMap = (Map<UserData, BotResponseWsDTO>) pjp.proceed(pjp.getArgs());
        }
        return botResponseMap;
    }

    private SendMessage getUserIsAutoBlockMessage(UserData user, String timeLeft) {
        ResourceBundle resourceBundle = user.getLanguage().getResourceBundle();
        String text = String.format(resourceBundle.getString
                ("you_are_auto_blocked"), timeLeft);
        return messageBuilder.getMsgHTML(user.getChatId(),
                text);
    }

    private SendMessage getUserIsAdminBlockMessage(UserData user) {
        ResourceBundle resourceBundle = user.getLanguage().getResourceBundle();
        return messageBuilder.getMsgHTML(user.getChatId(),
                resourceBundle.getString("you_are_blocked_by_admin"));
    }


    private long getTimeLeftTillUnblockMillis(UserData user) {
        Date blockDate = user.getBlockDate();
        LocalDateTime time = LocalDateTime
                .fromDateFields(blockDate);
        time = time.plusHours(autoBlockTimeHours);
        Date unblockTime = time.toDate();
        Date now = new Date();

        return unblockTime.getTime() - now.getTime();
    }

    private String getTimeLeftView(UserData user) {
        long millisLeft = getTimeLeftTillUnblockMillis(user);
        ResourceBundle resourceBundle = user.getLanguage().getResourceBundle();
        return timeHelper.millisToAppropriateHourView
                (millisLeft, resourceBundle);
    }

    private Date getAmountPassedAfterUserBlock(UserData user) {
        long timeMillisPassed = new Date().getTime() - user.getActivityDate().getTime();
        return new Date(timeMillisPassed);
    }

    private boolean canUserBeUnblocked(UserData user) {
        Date timePassed = getAmountPassedAfterUserBlock(user);
        Date timeRequired = new Date((long) autoBlockTimeHours * 60 * 60 * 1000);
        return timePassed.after(timeRequired);
    }

}
