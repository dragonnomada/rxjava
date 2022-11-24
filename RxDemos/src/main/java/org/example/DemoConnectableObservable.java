package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class DemoConnectableObservable {

    public static void main(String[] args) {

        ConnectableObservable<Long> source = Observable.interval(1, TimeUnit.SECONDS).publish();

        //Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS);

        source.subscribe(i -> System.out.printf("Observador 1: %d %n", i));

        try {
            Thread.sleep(4000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        source.subscribe(i -> System.out.printf("Observador 2: %d %n", i));

        source.connect();

        try {
            Thread.sleep(5000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        source.subscribe(i -> System.out.printf("Observador 3: %d %n", i));

        try {
            Thread.sleep(10000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
