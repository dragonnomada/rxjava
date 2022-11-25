package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Rx210Disposable {

    public static void main(String[] args) {

        Observable<Long> source1 = Observable.interval(1, TimeUnit.SECONDS);

        Disposable disposable1 = source1.subscribe(value -> System.out.printf("Observador 1: %d %n", value));

        System.out.printf("Observador 1 DISPOSED? %B %n", disposable1.isDisposed());

        sleep(5000);
        System.out.println("Han pasado 5 segundos");

        disposable1.dispose();

        System.out.printf("Observador 1 DISPOSED? %B %n", disposable1.isDisposed());

        sleep(5000);
        System.out.println("Han pasado 10 segundos");

        System.out.printf("Observador 1 DISPOSED? %B %n", disposable1.isDisposed());

        Observable<Long> source2 = Observable.interval(1, TimeUnit.SECONDS);

        Observable<Long> source3 = Observable.interval(7, TimeUnit.SECONDS);

        Disposable disposable2 = source2.subscribe(value ->  System.out.printf("Observador 2: %d %n", value));

        Disposable disposable3 = source3.first(0L).subscribe(value -> {
            System.out.println("Liberando al Observador 2, desde el observador 3");
            disposable2.dispose();
            System.out.println("Observador 2 liberado, desde el observador 3");
            System.out.printf("Observador 2 DISPOSED? %B %n", disposable2.isDisposed());
            System.out.println("Han pasado 7 segundos");
        });

        sleep(10000);
        System.out.println("Han pasado 10 segundos");
        System.out.printf("Observador 2 DISPOSED? %B %n", disposable2.isDisposed());
        System.out.printf("Observador 3 DISPOSED? %B %n", disposable3.isDisposed());

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
