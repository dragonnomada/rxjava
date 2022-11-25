package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx206Cold {

    public static void main(String[] args) {

        Observable<String> source = Observable.just("Abeja", "Borrego", "Cabra");

        source.subscribe(animal -> {
            System.out.printf("Observador 1: %s %n", animal);
        });

        source.subscribe(animal -> {
            System.out.printf("Observador 2: %s %n", animal);
        });

        source.map(animal -> animal.toUpperCase())
                .subscribe(animal -> {
                        System.out.printf("Observador 3: %s %n", animal);
                });

    }

}
