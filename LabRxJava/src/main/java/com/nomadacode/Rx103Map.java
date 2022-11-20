package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx103Map {

    public static void main(String[] args) {

        Observable<String> items = Observable.just("Uno", "Dos", "Tres", "Cuatro", "Cinco");

        Observable<Integer> itemLengths = items.map(item -> item.length());

        itemLengths.subscribe(itemLength -> System.out.println(itemLength));

        System.out.println("ok");

    }

}
