package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx104Interval {

    public static void main(String[] args) {

        Observable<Long> items = Observable.interval(1, TimeUnit.SECONDS);

        items.subscribe(item -> System.out.println(item));

        sleep(5000);

        System.out.println("ok");

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
