package com.self.house.renting.util;


import com.self.house.renting.constants.Constants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {
    public static LocalDateTime getTimeZoneDatetimeFromZone(LocalDateTime dateTime, String zone) {
        ZonedDateTime zonedUTC = dateTime.atZone(ZoneId.of(Constants.UTC_TIME));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of(zone));
                return zonedIST.toLocalDateTime();
    }
}
