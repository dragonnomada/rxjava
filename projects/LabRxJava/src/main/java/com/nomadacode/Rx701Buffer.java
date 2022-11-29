package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx701Buffer {

    public static void main(String[] args) {

        Observable.range(1, 20)
                .buffer(5, 6)
                .subscribe(System.out::println);

    }

}
