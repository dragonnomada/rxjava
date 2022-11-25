package com.nomadacode;

import io.reactivex.rxjava3.core.Completable;

public class Rx209Completable {

    public static void main(String[] args) {

        Completable.complete()
                .subscribe(() -> System.out.println("Proceso 1 COMPLETADO"));

        Completable.fromRunnable(() -> System.out.println("Ejecutando el Proceso 2"))
                .subscribe(() -> System.out.println("Proceso 2 COMPLETADO"));

        Completable.fromAction(() -> System.out.println("Ejecutando la Acción 3"))
                .subscribe(() -> System.out.println("Proceso 3 completado"));

    }

}
