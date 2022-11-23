package org.example;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;

public class DemoObservableWithChain {

    public static void main(String[] args) {

        Observable.just("manzana", "pera", "guanabana", "melón")
                .map(fruta -> String.format("Fruta: %s", fruta.toUpperCase()))
                .map(fruta -> String.format("[%d] %s", fruta.length(), fruta))
                .map(String::length)
                .reduce((maxima, longitud) -> maxima < longitud ? longitud : maxima)
                .subscribe(System.out::println);

        Observable<String> reportes = Observable.just("manzana", "pera", "guanabana", "melón")
                .map(fruta -> String.format("Fruta: %s", fruta.toUpperCase()))
                .map(fruta -> String.format("[%d] %s", fruta.length(), fruta));

        reportes.subscribe(System.out::println);
        reportes.subscribe(System.out::println);
        reportes.subscribe(System.out::println);

        reportes.map(String::length)
                .reduce((maxima, longitud) -> maxima < longitud ? longitud : maxima)
                .subscribe(System.out::println);

    }

}
