package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx102Hello {

    public static void main(String[] args) {

        Observable<String> items = Observable.just("Uno", "Dos", "Tres");

        items.subscribe(item -> System.out.println(item));

        System.out.println("Hello world!");

    }

}
