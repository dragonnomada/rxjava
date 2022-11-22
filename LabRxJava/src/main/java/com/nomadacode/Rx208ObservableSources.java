package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Rx208ObservableSources {

    public static void main(String[] args) {

        Observable.just("Uno", "Dos", "Tres", "Cuatro")
                .subscribe(System.out::println);

        Observable.create(emitter -> {
            emitter.onNext("One");
            emitter.onNext("Two");
            emitter.onNext("Three");
            emitter.onNext("Four");
            emitter.onComplete();
        }).subscribe(System.out::println);

        String[] fruits = new String[] { "Manzana", "Mango", "Pera", "Piña" };

        Observable.fromArray(fruits)
                .subscribe(System.out::println);

        List<String> names = new ArrayList<>();

        names.add("Pepe");
        names.add("Paco");
        names.add("Paty");

        Observable.fromIterable(names)
                .subscribe(System.out::println);

        Observable.range(1, 10)
                .subscribe(System.out::println);

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);

        Observable.empty()
                .subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("OK"));

        Observable.never()
                .subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("OK"));

        Observable.error(new Exception("Error inesperado"))
                .subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("OK"));

        Observable.fromCallable(() -> "Hola mundo")
                .subscribe(System.out::println);

        sleep(5000);

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
