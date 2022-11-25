package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx303TranformOperators {

    public static void main(String[] args) {

        System.out.println("map()");

        Observable.range(1, 5)
                .map(i -> i * i)
                .subscribe(System.out::println);

        System.out.println("flatMap()");

        Observable.range(1, 5)
                .flatMap(i -> Observable.just(i, i * i))
                //.window(2)
                //.flatMap(integerObservable -> integerObservable.reduce((s, i) -> s + i).toObservable())
                .subscribe(System.out::println);

        System.out.println("cast()");

        Observable.just((Object) "Hola", (Object) "Mundo")
                .cast(String.class)
                .subscribe(System.out::println);

        System.out.println("startWithItem()");

        Observable.range(1, 2)
                .startWithItem(0)
                .startWithItem(-1)
                .startWithItem(-2)
                .subscribe(System.out::println);

        System.out.println("startWithArray()");

        Observable.range(1, 2)
                .startWithArray(-2, -1, 0)
                .subscribe(System.out::println);

        System.out.println("sorted()");

        Observable.just("manzana", "Mango", "pera", "kiwi")
                //.sorted()
                .sorted((fruta1, fruta2) -> -1 * fruta1.compareToIgnoreCase(fruta2))
                .subscribe(System.out::println);

        System.out.println("scan()");

        Observable.range(1, 5)
                .scan((accum, i) -> accum + i)
                .subscribe(System.out::println);

    }

}
