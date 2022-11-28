package com.nomadacode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static void logDate(String prefix) {
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String date = dateObj.format(formatter);
        System.out.printf("%s%s%n", prefix, date);
    }

    public static void logDate() {
        Util.logDate("");
    }

}
