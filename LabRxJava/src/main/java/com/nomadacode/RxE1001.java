package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class RxE1001 {

    public static void main(String[] args) {

        String[] players = new String[] { "Jugador 1", "Jugador 2", "Jugador 3", "Jugador 4" };

        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);

        Observable<Integer> indices = interval.map(value -> (int)(value % players.length));

        Observable<String> turns = indices.map(index -> players[index]);

        turns.subscribe(player -> {
            System.out.printf("Hola %s, es tu turno %n", player);
        });

        sleep(8001);

        System.out.println("ok");

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
