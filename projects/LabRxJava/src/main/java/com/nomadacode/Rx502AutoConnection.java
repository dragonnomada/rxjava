package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class Rx502AutoConnection {

    public static void main(String[] args) {

        Observable<Long> sourceInterval = Observable.interval(1, TimeUnit.SECONDS)
                .publish()
                .autoConnect(2);

        sourceInterval.subscribe(i -> System.out.printf("[1]: %d %n", i));

        Sleep.sleep(2000);

        sourceInterval.subscribe(i -> System.out.printf("[2]: %d %n", i));

        Sleep.sleep(4000);

        sourceInterval.subscribe(i -> System.out.printf("[3]: %d %n", i));

        Sleep.sleep(2000);

    }

}
