package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx203Chaining {

    public static void main(String[] args) {

        Observable.just("Uno", "Dos", "Tres", "Cuatro", "Cinco")
                .map(String::length)
                .subscribe(System.out::println);

        Observable.just(1, 2, 3, 4, 5, 6)
                .map(x -> Math.pow(x, 2))
                .map(y -> (float)(y / Math.PI))
                .map(z -> (int)Math.abs(z))
                .subscribe(System.out::println);

    }

}
