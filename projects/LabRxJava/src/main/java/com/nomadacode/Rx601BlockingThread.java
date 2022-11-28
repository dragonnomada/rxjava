package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx601BlockingThread {

    public static void main(String[] args) {

        Observable<Integer> observable = Observable.range(1, 5)
                .map(i -> {
                    // Bloqueamos de 1 a 2 segundos
                    Sleep.sleep((long) (Math.random() * 1000 + 1000));
                    return i;
                });

        Util.logDate();

        // El primer observador bloqueará el programa de 5 a 10 segundos
        observable.subscribe(i -> System.out.printf("[1]: %d %n", i));
        // El segundo observador bloqueará el programa de 5 a 10 segundos
        observable.subscribe(i -> System.out.printf("[2]: %d %n", i));

        Util.logDate();

    }

}
