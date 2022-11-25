package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoObservableStaticMethodReference {

    public static void main(String[] args) {

        Observable<String> frutas = Observable.just("Manzana", "Mango", "kiwi");

        //Observable<Integer> frutasLegth = frutas.map(fruta -> fruta.length());

        Observable<Integer> frutasLegth = frutas.map(String::length);

        //frutasLegth.subscribe(fruta -> System.out.println(fruta));

        frutasLegth.subscribe(System.out::println);

    }

}
