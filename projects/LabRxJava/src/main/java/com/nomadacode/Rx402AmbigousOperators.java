package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Rx402AmbigousOperators {

    public static void main(String[] args) {

        Observable.amb(
                        Arrays.asList(
                                Observable.interval(300, TimeUnit.MILLISECONDS)
                                        .map(i -> String.format("Source 1: %d", i))
                                        .flatMap(i -> Observable.just(i).delay((long)(Math.random() * 100), TimeUnit.MILLISECONDS)),
                                Observable.interval(300, TimeUnit.MILLISECONDS)
                                        .map(i -> String.format("Source 2: %d", i))
                                        .flatMap(i -> Observable.just(i).delay((long)(Math.random() * 100), TimeUnit.MILLISECONDS))
                        )
                )
                .subscribe(System.out::println);

        Sleep.sleep(2000);

    }

}
