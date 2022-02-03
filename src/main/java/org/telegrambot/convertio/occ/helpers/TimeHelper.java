package org.telegrambot.convertio.occ.helpers;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TimeHelper {

    public String millisToAppropriateHourView(long millis, ResourceBundle resourceBundle) {

        String hourStr = resourceBundle.getString("hour");
        String minStr = resourceBundle.getString("min");
        String secStr = resourceBundle.getString("sec");
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        return String.format("%02d%s %02d%s %02d%s",
                hours, hourStr, minutes, minStr, seconds, secStr
        );
    }

    public String millisToAppropriateDayView(long millis, ResourceBundle resourceBundle) {

        String dayStr = resourceBundle.getString("dayd");
        String hourStr = resourceBundle.getString("hour");
        String minStr = resourceBundle.getString("min");
        String secStr = resourceBundle.getString("sec");

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) -
                TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        return String.format("%02d%s %02d%s %02d%s %02d%s",
                days, dayStr, hours, hourStr, minutes, minStr, seconds, secStr
        );
    }

}
