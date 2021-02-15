package com.pongo.taf.utils;

import java.time.LocalDate;

public class DateOperations {

    public static LocalDate calculateDateInTheFuture(int years, int months, int days) {
        return LocalDate.now().plusYears(years).plusMonths(months).plusDays(days);
    }

    public static LocalDate calculateDateInThePast(int years, int months, int days) {
        return LocalDate.now().minusYears(years).minusMonths(months).minusDays(days);
    }

}
