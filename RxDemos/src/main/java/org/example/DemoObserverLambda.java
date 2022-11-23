package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DemoObserverLambda {

    public static void main(String[] args) {

        System.out.println("Establecion la conexión...");
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(
                        valor -> System.out.printf("Esperando (%d)... %n", valor),
                        error -> System.out.println("Falló la conexión"),
                        () -> System.out.println("Se estableció la conexión correctamente")
                );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
