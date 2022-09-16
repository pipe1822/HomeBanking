package com.mindhub.homebanking.Utils;

import org.jetbrains.annotations.NotNull;

public final class CardUtils {
    private CardUtils() {
    }

    public static int getCvv() {
        return Math.toIntExact((Math.round(Math.random() * ((999 - 100) + 1) + 100)));
    }

    @NotNull
    public static String getCardNumber() {
        String number;
        number = Math.round(Math.random() * ((9999 - 1000) + 1) + 1000) + "-" + Math.round(Math.random() * ((9999 - 1000) + 1) + 1000) + "-" + Math.round(Math.random() * ((9999 - 1000) + 1) + 1000) + "-" + Math.round(Math.random() * ((9999 - 1000) + 1) + 1000);
        return number;
    }
}
