package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx902LiftCustomOperator {

    public static void main(String[] args) {

        Observable.just("Fresa", "Mango", "Guanabana", "Melocotón", "Sandía")
                .lift(Rx902CustomOperators.charset())
                .sorted()
                .subscribe(System.out::println);

    }

}
