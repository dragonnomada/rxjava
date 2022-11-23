package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoObservableMethodReference {

    public static void main(String[] args) {

        Observable<String> frutas = Observable.just("Manzana", "Mango", "Pera", "Piña");

        frutas.subscribe(fruta -> System.out.println(fruta));

        frutas.subscribe(System.out::println);

    }

}
