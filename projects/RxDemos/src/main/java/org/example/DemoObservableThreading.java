package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoObservableThreading {

    public static void main(String[] args) {

        Util.logDate("[COMIENZA] ");

        //Observable.range(1, 5)
        //        .subscribeOn(Schedulers.computation())
        //        .flatMap(i -> Observable.just(i).delay(1, TimeUnit.SECONDS))
        //        .flatMap(i -> Observable.interval(1, TimeUnit.SECONDS).take(i))
        //        .subscribe(System.out::println);

        // 1

        Observable.range(1, 10)
                .subscribeOn(Schedulers.io()) // piscina de hilos
                .doOnComplete(() -> {
                    Util.logDate("[Observador] Completado el ");
                    System.out.println("Recursos liberados");
                })
                .map(i -> {
                    Sleep.sleep(1000);
                    return i * i;
                }).subscribe(
                        i -> {
                            System.out.println(Thread.currentThread().getName());
                        });

        // 2

        Sleep.sleep(15000);

        Util.logDate("[TERMINA] ");

    }

}
