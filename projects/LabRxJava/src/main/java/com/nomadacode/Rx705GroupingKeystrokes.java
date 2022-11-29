package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Rx705GroupingKeystrokes {

    static Observable<Integer> getFeed(int feed) {
        switch (feed) {
            case 1:
                return Observable.range(1, 10);
            case 2:
                return Observable.interval(1, TimeUnit.SECONDS)
                        .map(i -> (int) i.longValue())
                        .take(10);
        }
        return Observable.error(new Exception("Feed no válido"));
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Subject<Integer> selectorOpcion = PublishSubject.create();

        selectorOpcion.throttleWithTimeout(1, TimeUnit.SECONDS)
                .switchMap(opcion -> {
                    System.out.printf("La opción seleccionada es: %d %n", opcion);
                    return getFeed(opcion);
                }).subscribe(System.out::println);

        while (true) {

            System.out.println("Selecciona una opción:");
            System.out.println("1. Feed 1");
            System.out.println("2. Feed 2");
            System.out.println("Salir");

            System.out.print("Opción: ");

            int opcion = scanner.nextInt();

            selectorOpcion.onNext(opcion);

        }

    }

}
