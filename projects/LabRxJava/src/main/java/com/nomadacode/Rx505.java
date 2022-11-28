package com.nomadacode;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class Rx505 {

    public static void main(String[] args) {

        // (1) Creamos el Subject que representa un dispositivo de emisiones
        Subject<Integer> subject = PublishSubject.create();

        // (2) Suscribimos un observador
        subject.map(i -> String.format("<< %d >>", i))
                .subscribe(s -> System.out.printf("[1]: %s %n", s));

        // (3) Propagamos las emisiones
        for (int i = 1; i <= 5; i++) {
            subject.onNext(i);
        }

        // (4) Notificamos que las emisiones se han completado
        subject.onComplete();

    }

}
