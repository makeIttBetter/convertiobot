package org.telegrambot.convertio.facades.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ScheduledService {
    private static final ScheduledExecutorService executorService
            = Executors.newScheduledThreadPool(2);
    private final DDosCheckTask ddosTask;

    public void startScheduledTasks() {
        executorService.scheduleAtFixedRate(ddosTask, 0, 10, TimeUnit.MINUTES);
    }
}
