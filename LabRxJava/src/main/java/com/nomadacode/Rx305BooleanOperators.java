package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx305BooleanOperators {

    public static void main(String[] args) {

        System.out.println("all()");

        Observable.range(1, 5)
                .all(i -> i % 2 == 0)
                .subscribe(System.out::println);

        System.out.println("any()");

        Observable.range(1, 5)
                .any(i -> i % 2 == 0)
                .subscribe(System.out::println);

        System.out.println("isEmpty()");

        Observable.range(1, 0)
                .isEmpty()
                .subscribe(System.out::println);

        System.out.println("contains()");

        Observable.range(1, 5)
                .contains(3)
                .subscribe(System.out::println);

        System.out.println("sequenceEqual()");

        Observable.sequenceEqual(
                Observable.range(1, 5),
                Observable.just(1, 2, 3, 4, 5)
        ).subscribe(System.out::println);

    }

}
