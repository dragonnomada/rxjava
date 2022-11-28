package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Rx601Concurrency {

    public static void main(String[] args) {

        // Versión thread-safe

        Observable<Integer> observable = Observable.range(1, 5)
                .subscribeOn(Schedulers.computation())
                .map(i -> {
                    // Bloqueamos de 1 a 2 segundos
                    Sleep.sleep((long) (Math.random() * 1000 + 1000));
                    return i;
                });

        Util.logDate();

        // El primer observador no bloquerá el hilo, pero se completará en máximo 10 segundos
        observable.subscribe(
                i -> System.out.printf("[1]: %d %n", i),
                Throwable::printStackTrace,
                () -> Util.logDate("[1] COMPLETADO: ")
        );
        // El segundo observador no bloquerá el hilo, pero se completará en máximo 10 segundos
        observable.subscribe(
                i -> System.out.printf("[2]: %d %n", i),
                Throwable::printStackTrace,
                () -> Util.logDate("[2] COMPLETADO: ")
        );

        // Esperamos máximo 10 segundos para que ambos terminen
        Sleep.sleep(10000);

    }

}
