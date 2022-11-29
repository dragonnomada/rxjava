package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx701BufferBoundary {

    public static void main(String[] args) {

        Observable.interval(800, TimeUnit.MILLISECONDS)
                .timestamp()
                .buffer(Observable.interval(3, TimeUnit.SECONDS))
                .blockingSubscribe(System.out::println);

    }

}
