package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Rx301ConditionalOperators {

    public static void main(String[] args) {

        System.out.println("takeWhile()");

        Observable.range(1, 100)
                .takeWhile(i -> i <= 5)
                .subscribe(System.out::println);

        System.out.println("takeUntil()");

        Observable.interval(500, TimeUnit.MILLISECONDS)
                .takeUntil(Observable.interval(2, TimeUnit.SECONDS))
                .subscribe(System.out::println);

        try {
            Thread.sleep(2000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("skipWhile()");

        Observable.range(1, 100)
                .skipWhile(i -> i <= 95)
                .subscribe(System.out::println);

        System.out.println("skipUntil()");

        Observable.interval(500, TimeUnit.MILLISECONDS)
                .skipUntil(Observable.interval(2, TimeUnit.SECONDS))
                .subscribe(System.out::println);

        try {
            Thread.sleep(4000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("defaultIfEmpty()");

        Observable.empty()
                .defaultIfEmpty("No hay nada :(")
                .subscribe(System.out::println);

        System.out.println("switchIfEmpty()");

        Observable.empty()
                .switchIfEmpty(Observable.just("Uno", "Dos", "Tres"))
                .subscribe(System.out::println);

    }

}
