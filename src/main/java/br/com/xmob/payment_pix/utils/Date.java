package br.com.xmob.payment_pix.utils;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Log4j2
public abstract class Date {

    public static LocalDateTime adjustDate(String date){
        log.debug("[start] Date - adjustDate");
        log.debug("[Date] {}", date);
        OffsetDateTime adjustedDateTime;
        try {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(date, FORMATTER);
            adjustedDateTime = offsetDateTime.withOffsetSameInstant(ZoneOffset.of("-03:00"));
        } catch (Exception e){
           return LocalDateTime.now();
        }
        log.debug("[finish] Date - adjustDate");
        return adjustedDateTime.toLocalDateTime();
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

}
