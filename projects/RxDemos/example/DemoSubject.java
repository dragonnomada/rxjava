package org.example;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class DemoSubject {

    public static void main(String[] args) {

        // Un Subject es Observable y Observador al mismo tiempo
        // Podemos suscribir y emitir al mismo tiempo
        // Funciona como un Observable<String>
        // Funciona como un emitter.onNext(<String>)
        Subject<String> logger = PublishSubject.create();

        logger.subscribe(message -> System.out.printf("[1]: %s %n", message));
        logger.subscribe(message -> System.out.printf("[2]: %s %n", message));
        logger.subscribe(message -> System.out.printf("[3]: %s %n", message));

        for (int i = 0; i < 100; i++) {
            double x = i * i;
            double y = 2 * i + 1;
            double z = i / 3;

            logger.onNext(String.format("(%.2f, %.2f, %.2f)", x, y, z));
        }

    }

}
