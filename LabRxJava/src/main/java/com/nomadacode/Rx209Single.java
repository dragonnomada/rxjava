package com.nomadacode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class Rx209Single {

    public static void main(String[] args) {

        Single<String> observable1 = Single.just("VALOR 1");

        SingleObserver<String> observer1 = new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Inicio del observador 1");
            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.printf("El valor recibido por el observador 1 es: %s %n", s);
                System.out.println("Fin del observador 1");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.printf("ERROR en el observador 1: %s %n", e.getMessage());
            }
        };

        observable1.subscribe(observer1);

        Single<String> observable2 = Single.just("VALOR 2");

        Consumer<String> onSuccess = value -> System.out.printf("El valor recibido por el observador 2 es: %s %n", value);
        Consumer<Throwable> onError = e -> System.out.printf("ERROR en el observador 2: %s %n", e.getMessage());

        observable2.subscribe(
                onSuccess,
                onError
        );

        Single.just("VALOR 3").subscribe(
                value -> System.out.printf("El valor recibido por el observador 3 es: %s %n", value),
                e -> System.out.printf("ERROR en el observador 3: %s %n", e.getMessage())
        );

    }

}
