package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx701BufferTime {

    public static void main(String[] args) {

        Observable.interval(200, TimeUnit.MILLISECONDS)
                .buffer(1, TimeUnit.SECONDS, 3)
                .blockingSubscribe(System.out::println);

    }

}
