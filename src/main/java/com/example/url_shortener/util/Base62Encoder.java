package com.example.url_shortener.util;

public class Base62Encoder {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int BASE = 62;

    public static String encode(long number) {
        if (number == 0) {
            return String.valueOf(BASE62.charAt(0));
        }

        StringBuilder sb = new StringBuilder();

        while (number > 0) {
            int remainder = (int) (number % BASE);
            sb.append(BASE62.charAt(remainder));
            number /= BASE;
        }

        return sb.reverse().toString();
    }
}
