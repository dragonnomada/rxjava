package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx302SuppressOperators {

    public static void main(String[] args) {

        System.out.println("filter()");

        Observable.range(1, 100)
                .filter(i -> i % 20 == 0)
                .subscribe(System.out::println);

        System.out.println("take()");

        Observable.range(1, 100)
                .take(5)
                .subscribe(System.out::println);

        System.out.println("skip()");

        Observable.range(1, 100)
                .skip(95)
                .subscribe(System.out::println);

        System.out.println("distinct()");

        Observable.just("manzana", "pera", "mango", "manzana", "pera", "manzana")
                .distinct()
                .subscribe(System.out::println);

        System.out.println("distinct() [2]");

        Observable.just("uno", "dos", "tres", "cuatro", "cinco", "seis")
                .distinct(fruta -> fruta.length())
                .subscribe(System.out::println);

        System.out.println("distinctUntilChanged()");

        Observable.just(1, 1, 2, 2, 2, 3, 1, 1, 2, 3)
                .distinctUntilChanged()
                .subscribe(System.out::println);

        System.out.println("elementAt()");

        Observable.range(10, 13)
                .elementAt(4)
                .subscribe(System.out::println);

    }

}
