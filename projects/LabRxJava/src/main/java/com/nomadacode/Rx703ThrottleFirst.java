package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx703ThrottleFirst {

    public static void main(String[] args) {

        Observable.interval(200, TimeUnit.MILLISECONDS)
                .map(i -> i * 200)
                .throttleFirst(1, TimeUnit.SECONDS)
                .blockingSubscribe(System.out::println);

    }

}
