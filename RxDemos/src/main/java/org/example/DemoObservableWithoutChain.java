package org.example;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;

public class DemoObservableWithoutChain {

    public static void main(String[] args) {

        Observable<String> frutas = Observable.just("manzana", "pera", "guanabana", "melón");

        Observable<String> frutasConDescripcion = frutas.map(fruta -> String.format("Fruta: %s", fruta.toUpperCase()));

        Observable<String> frutasConDescripcionYLongitud = frutasConDescripcion.map(fruta -> String.format("[%d] %s", fruta.length(), fruta));

        Observable<Integer> frutasLongitudFinalReporte = frutasConDescripcionYLongitud.map(String::length);

        Maybe<Integer> frutasMaxLongitudReporte = frutasLongitudFinalReporte.reduce((maxima, longitud) -> maxima < longitud ? longitud : maxima);

        frutas.subscribe(System.out::println);
        frutasConDescripcion.subscribe(System.out::println);
        frutasConDescripcionYLongitud.subscribe(System.out::println);
        frutasLongitudFinalReporte.subscribe(System.out::println);

        frutasMaxLongitudReporte.subscribe(System.out::println);

    }

}
