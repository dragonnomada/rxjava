package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class DemoDisposing {

    public static void main(String[] args) {

        Observable<Long> sourceInterval = Observable.interval(1, TimeUnit.SECONDS);

        Disposable disposable = sourceInterval.subscribe(i -> System.out.printf("Observador 1: %d %n", i));

        System.out.println(disposable.isDisposed() ? "LIBERADO" : "VIVO");
        try {
            Thread.sleep(4000);
            System.out.println("Han pasado 4 segundos");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println(disposable.isDisposed() ? "LIBERADO" : "VIVO");

        // Liberar al observador
        disposable.dispose();

        System.out.println(disposable.isDisposed() ? "LIBERADO" : "VIVO");

        try {
            Thread.sleep(5000);
            System.out.println("Han pasado 5 segundos");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println(disposable.isDisposed() ? "LIBERADO" : "VIVO");

    }

}
