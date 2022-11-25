package com.nomadacode;

import io.reactivex.rxjava3.core.Maybe;

public class Rx209Maybe {

    public static void main(String[] args) {

        Maybe.just("Proceso 1")
                .subscribe(
                        value -> System.out.printf("RECIBIDO: %s %n", value),
                        Throwable::printStackTrace,
                        () -> System.out.println("El proceso 1 ha finalizado")
                );

        Maybe.empty()
                .subscribe(
                        value -> System.out.printf("RECIBIDO: %s %n", value),
                        Throwable::printStackTrace,
                        () -> System.out.println("El proceso 2 ha finalizado")
                );

    }

}
