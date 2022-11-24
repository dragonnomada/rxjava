package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoObservableList {

    public static void main(String[] args) {

        List<String> listaFrutas = new ArrayList<>();

        listaFrutas.add("pera");
        listaFrutas.add("manzana");
        listaFrutas.add("mango");

        Observable<List<String>> sourceListaFrutas = Observable.just(listaFrutas);

        sourceListaFrutas.subscribe(listaFrutas1 -> {
            listaFrutas1.add("Kiwi");
        });

        // ... en otro momento y lugar

        sourceListaFrutas.map(listaFrutas1 -> Arrays.toString(listaFrutas1.toArray()))
                .subscribe(System.out::println);

        Observable<String> frutas = Observable.fromIterable(listaFrutas);

        frutas.startWithArray("Frutas:", "-------")
                .subscribe(System.out::println);

        sourceListaFrutas.subscribe(listaFrutas1 -> listaFrutas1.add("piña"));

        frutas.startWithArray("Frutas:", "-------")
                .subscribe(System.out::println);


    }

}
