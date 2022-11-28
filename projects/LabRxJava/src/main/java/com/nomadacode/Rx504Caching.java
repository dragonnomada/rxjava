package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Rx504Caching {

    public static void main(String[] args) {

        Observable<String> sourceInterval = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> {
                    LocalDateTime dateObj = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                    String date = dateObj.format(formatter);
                    return String.format("Emisión %d a las %s", i, date);
                })
                .take(5)
                .cache();

        sourceInterval.subscribe(s -> System.out.printf("[1] %s %n", s));

        Sleep.sleep(5000);

        sourceInterval.subscribe(s -> System.out.printf("[2] %s %n", s));



    }

}
