package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx309UtilOperators {

    public static void main(String[] args) {

        System.out.println("delay()");

        Observable.just("A", "B")
                .delay(3, TimeUnit.SECONDS)
                .subscribe(System.out::println);

        try {
            Thread.sleep(4000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("repeat()");

        Observable.just("A", "B")
                .repeat(2)
                .subscribe(System.out::println);

        System.out.println("single()");

        Observable.empty()
                .single("B")
                .subscribe(System.out::println);

        System.out.println("timestamp()");

        Observable.just("A", "B")
                .timestamp(TimeUnit.SECONDS)
                .subscribe(System.out::println);

        System.out.println("timeInterval()");

        Observable.interval(700, TimeUnit.MILLISECONDS)
                .timeInterval(TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

        try {
            Thread.sleep(6000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
