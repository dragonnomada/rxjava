package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx403ZippingOperators {

    public static void main(String[] args) {

        Observable.zip(
                Observable.just("A", "B", "C").repeat(2),
                Observable.interval(1, TimeUnit.SECONDS),
                Observable.just(true, false, true).repeat(2),
                (s1, s2, s3) -> String.format("[%s | %d | %B]", s1, s2, s3)
        ).subscribe(System.out::println);

        Sleep.sleep(5000);

    }

}
