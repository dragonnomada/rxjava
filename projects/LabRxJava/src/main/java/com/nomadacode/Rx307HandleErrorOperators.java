package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx307HandleErrorOperators {

    public static void main(String[] args) {

        System.out.println("onErrorReturn()");

        Observable.error(new Exception("Error"))
                .onErrorReturn(throwable -> 0)
                .subscribe(System.out::println);

        System.out.println("onErrorReturnItem()");

        Observable.error(new Exception("Error"))
                .onErrorReturnItem(0)
                .subscribe(System.out::println);

        System.out.println("onErrorResumeWith()");

        Observable.error(new Exception("Error"))
                .onErrorResumeWith(Observable.just(1, 2, 3))
                .subscribe(System.out::println);

        System.out.println("onErrorResumeNext()");

        Observable.error(new Exception("Error"))
                .onErrorResumeNext(throwable -> Observable.just(4, 5, 6))
                .subscribe(System.out::println);

        System.out.println("retry()");

        Observable.just(1, 5, 0, 4, 2)
                .map(i -> {
                    if (i == 0) throw new Exception("Error");
                    return i;
                })
                .retry(3)
                .subscribe(
                        System.out::println,
                        throwable -> System.out.printf("ERROR: %s %n", throwable.getMessage())
                );

    }

}
