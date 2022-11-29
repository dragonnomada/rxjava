package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx702Window {

    public static void main(String[] args) {

        Observable.range(1, 20)
                .window(5, 6)
                .flatMapSingle(observable -> observable.reduce(0, (sum, i) -> sum + i))
                .subscribe(System.out::println);

    }

}
