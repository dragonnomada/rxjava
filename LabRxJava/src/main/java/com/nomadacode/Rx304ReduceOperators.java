package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx304ReduceOperators {

    public static void main(String[] args) {

        System.out.println("count()");

        Observable.range(1, 5)
                .count()
                .subscribe(System.out::println);

        System.out.println("reduce()");

        Observable.range(1, 5)
                .reduce((accum, i) -> accum + i)
                .subscribe(System.out::println);

    }

}
