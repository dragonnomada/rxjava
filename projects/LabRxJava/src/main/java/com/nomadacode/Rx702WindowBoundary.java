package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx702WindowBoundary {

    public static void main(String[] args) {

        Observable.interval(800, TimeUnit.MILLISECONDS)
                .window(Observable.interval(3, TimeUnit.SECONDS))
                .flatMapSingle(observable -> observable.reduce(0L, (sum, i) -> sum + i))
                .blockingSubscribe(System.out::println);

    }

}
