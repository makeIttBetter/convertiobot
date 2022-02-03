package org.telegrambot.convertio.facades.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.services.UserService;

/**
 * Here we are checking are there any violators
 * that tried to slow down the server
 */
@Component
@RequiredArgsConstructor
public class DDosCheckTask implements Runnable {
    private final UserService userService;

    @Override
    public void run() {
        userService.userBlockTransactionRun();
    }
}
