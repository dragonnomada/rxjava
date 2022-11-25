package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx405GroupingOperators {

    public static void main(String[] args) {

        Observable.range(1, 100)
                .groupBy(i -> i % 4)
                .flatMapSingle(group -> group.toList())
                .subscribe(System.out::println);

    }

}
