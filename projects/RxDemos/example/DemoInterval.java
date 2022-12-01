package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import java.util.concurrent.TimeUnit;

public class DemoInterval {

    public static void main(String[] args) {

        Observable<Long> tics = Observable.interval(2, TimeUnit.SECONDS);

        tics.subscribe(tic -> System.out.println(tic));

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
