package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx702WindowTime {

    public static void main(String[] args) {

        Observable.interval(200, TimeUnit.MILLISECONDS)
                .window(1, TimeUnit.SECONDS, 3)
                .flatMapSingle(observable -> observable.reduce(0L, (sum, i) -> sum + i))
                .blockingSubscribe(System.out::println);

    }

}
