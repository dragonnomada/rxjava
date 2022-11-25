package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

public class Rx207Connectable {

    public static void main(String[] args) {

        ConnectableObservable<String> source = Observable.just("Alfa", "Beta", "Gama", "Delta", "Epsilon").publish();

        source.subscribe(System.out::println);
        source.map(String::toUpperCase).subscribe(System.out::println);
        source.map(String::length).subscribe(System.out::println);

        source.connect();

    }

}
