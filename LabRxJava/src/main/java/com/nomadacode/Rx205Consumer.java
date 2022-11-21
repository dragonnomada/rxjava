package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;

public class Rx205Consumer {

    public static void main(String[] args) {

        Consumer<String> onNext = item -> {
            System.out.printf("Se ha recibido: %s %n", item);
        };

        Consumer<Throwable> onError = e -> {
            System.out.printf("ERROR: %s %n", e.getMessage());
        };

        Action onComplete = () -> {
            System.out.println("FIN del observable :D");
        };

        Observable.just("A", "B", "C", "D")
                .subscribe(onNext, onError, onComplete);

        Observable.just("X", "Y", "Z", "W")
                .subscribe(
                        item -> System.out.printf("Se ha recibido: %s %n", item),
                        e -> System.out.printf("ERROR: %s %n", e.getMessage()),
                        () -> System.out.println("FIN del observable :D")
                );

    }

}
