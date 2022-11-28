package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DemoMulticastingAutoconnect {

    public static void main(String[] args) {

        Observable<Double> sourceRandom = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> Math.random() * 100.0)
                .publish()
                .autoConnect(3);

        sourceRandom.subscribe(r -> System.out.printf("[1]: %.2f %n", r));

        Sleep.sleep(2000);

        sourceRandom.subscribe(r -> System.out.printf("[2]: %.2f %n", r));



        Sleep.sleep(10000);

    }

}
