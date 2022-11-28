package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Rx503Replay {

    public static void main(String[] args) {

        Observable<String> sourceInterval = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> {
                    LocalDateTime dateObj = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                    String date = dateObj.format(formatter);
                    return String.format("Emisión %d a las %s", i, date);
                })
                //.replay()
                //.replay(3)
                //.replay(2, TimeUnit.SECONDS)
                .replay(3, 2, TimeUnit.SECONDS)
                .autoConnect();

        sourceInterval.subscribe(s -> System.out.printf("[1] %s %n", s));

        Sleep.sleep(4000);

        sourceInterval.subscribe(s -> System.out.printf("[2] %s %n", s));

        Sleep.sleep(4000);

    }

}
