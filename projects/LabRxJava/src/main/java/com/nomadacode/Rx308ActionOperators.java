package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Rx308ActionOperators {

    public static void main(String[] args) {

        System.out.println("doOnNext()");

        Observable.just("A", "B")
                .doOnNext(s -> System.out.printf("Next: %s %n", s))
                .subscribe(s -> System.out.printf("Observer: %s %n", s));

        System.out.println("doAfterNext()");

        Observable.just("A", "B")
                .doAfterNext(s -> System.out.printf("Next: %s %n", s))
                .subscribe(s -> System.out.printf("Observer: %s %n", s));

        System.out.println("doOnComplete()");

        Observable.just("A", "B")
                .doOnComplete(() -> System.out.println("Completado"))
                .subscribe(s -> System.out.printf("Observer: %s %n", s));

        System.out.println("doOnError()");

        Observable.error(new Exception("Ocurrió un error"))
                .doOnError(throwable -> System.out.println(throwable.getMessage()))
                .subscribe(
                        s -> System.out.printf("Observer: %s %n", s),
                        throwable -> System.out.println("Error")
                );

        System.out.println("doOnSubscribe()");

        Observable.just("A", "B")
                .doOnSubscribe(disposable -> System.out.println("Inicia"))
                .subscribe(s -> System.out.printf("Observer: %s %n", s));

        System.out.println("doOnSubscribe()");

        Observable.just("A", "B")
                .doOnDispose(() -> System.out.println("Finaliza"))
                .subscribe(s -> System.out.printf("Observer: %s %n", s));

        System.out.println("doOnSuccess()");

        Single.just("A")
                .doOnSuccess(s -> System.out.printf("Success: %s %n", s))
                .subscribe(s -> System.out.printf("Observer: %s %n", s));

        System.out.println("doFinally()");

        Observable.error(new Exception("Ocurrió un error"))
                .doFinally(() -> System.out.println("Finalizado"))
                .subscribe(
                        s -> System.out.printf("Observer: %s %n", s),
                        throwable -> System.out.println("Error")
                );

    }

}
