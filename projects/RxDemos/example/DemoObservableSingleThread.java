package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class DemoObservableSingleThread {

    public static void main(String[] args) {

        Observable<Integer> source = Observable.range(1, 999_999)
                .subscribeOn(Schedulers.single())
                //.subscribeOn(Schedulers.computation())
                .flatMap(i -> {
                    //Sleep.sleep(100);
                    return Observable.just(i * i).delay(100, TimeUnit.MILLISECONDS);
                }).publish().autoConnect(3);

        source.subscribe(i -> {
            System.out.printf("[1]: <<%s>> (%d) %n", Thread.currentThread().getName(), i);
        });
        source.subscribe(i -> {
            System.out.printf("[2]: <<%s>> (%d) %n", Thread.currentThread().getName(), i);
        });
        source.subscribe(i -> {
            System.out.printf("[3]: <<%s>> (%d) %n", Thread.currentThread().getName(), i);
        });

        source.blockingSubscribe();

        // ...

    }

}
