package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class DemoObservableAmb {

    public static void main(String[] args) {

        Observable<Double> sourcePrecios = Observable.interval(300, TimeUnit.SECONDS)
                .firstElement()
                .map(i -> 9.99)
                .toObservable();

        Observable<Double> sourceDefault = Observable.interval(10, TimeUnit.SECONDS)
                .firstElement()
                .map(i -> 0.0)
                .toObservable()
                .flatMap(i -> {
                    throw new Exception("Ya pason 10 segundos");
                });

        sourcePrecios.ambWith(sourceDefault)
                .subscribe(
                        precio -> System.out.println(precio),
                        throwable -> System.out.println(throwable.getMessage())
                );

        Sleep.sleep(20000);


    }

}
