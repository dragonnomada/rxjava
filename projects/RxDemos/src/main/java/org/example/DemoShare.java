package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class DemoShare {

    public static void main(String[] args) {

        Observable<Long> sourceInterval = Observable.interval(1, TimeUnit.SECONDS)
                .share();

        Sleep.sleep(1000);

        System.out.println("Llegó el primer suscriptor");
        Disposable d1 = sourceInterval.subscribe(i -> System.out.printf("[1]: %d %n", i));

        Sleep.sleep(2000);

        System.out.println("Llegó el segundo suscriptor");
        Disposable d2 = sourceInterval.subscribe(i -> System.out.printf("[2]: %d %n", i));

        Sleep.sleep(2000);

        System.out.println("Se libera el primer suscriptor");
        d1.dispose();

        Sleep.sleep(2000);

        System.out.println("Se libera el segundo suscriptor");
        d2.dispose();

        Sleep.sleep(2000);

        System.out.println("No hay suscriptores :(");
        System.out.println("Se reinicia");

        Sleep.sleep(2000);

        System.out.println("Llegó el tercer suscriptor (pero en términos reales es el primero)");
        Disposable d3 = sourceInterval.subscribe(i -> System.out.printf("[3]: %d %n", i));

        Sleep.sleep(10000);

    }

}
