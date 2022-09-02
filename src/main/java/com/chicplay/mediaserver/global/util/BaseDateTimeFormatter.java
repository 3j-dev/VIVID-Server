package com.chicplay.mediaserver.global.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class BaseDateTimeFormatter {

    public static DateTimeFormatter getLocalTimeFormatter() {
        return DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    public static DateTimeFormatter getLocalDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                .withLocale( Locale.KOREA )
                .withZone(ZoneId.systemDefault());
    }
}
