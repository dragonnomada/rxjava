package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DemoMulticastingAutoconnect {

    public static void main(String[] args) {

        Observable<Double> sourceRandom = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> Math.random() * 100.0)
                .publish()
                .autoConnect(3);

        System.out.println("Llegó el primer observador");

        sourceRandom.subscribe(r -> System.out.printf("[1]: %.2f %n", r));

        Sleep.sleep(2000);

        System.out.println("Llegó el segundo observador");

        sourceRandom.subscribe(r -> System.out.printf("[2]: %.2f %n", r));

        Sleep.sleep(4000);

        System.out.println("Llegó el tercer observador");

        sourceRandom.subscribe(r -> System.out.printf("[3]: %.2f %n", r));

        Sleep.sleep(2000);

        System.out.println("Llegó el cuarto observador");

        sourceRandom.subscribe(r -> System.out.printf("[4]: %.2f %n", r));

        Sleep.sleep(10000);

    }

}
