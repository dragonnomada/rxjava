package org.example;

import java.util.concurrent.atomic.AtomicReference;

public class DemoObservableSync {

    public static void main(String[] args) {

        // 1. Crear un objeto que espere los resultados
        AtomicReference<String> atomicReference = new AtomicReference<>();

        RandomUserRequestObservable.request()
                        // 2. Bloquear el hilo para esperar al observable
                        .blockingSubscribe(result -> {
                            atomicReference.set(result);
                            System.out.println(Thread.currentThread().getName());
                        });

        // 3. Consumir el objeto esperado
        System.out.printf("Resultado: %s %n", atomicReference.get());

    }

}
