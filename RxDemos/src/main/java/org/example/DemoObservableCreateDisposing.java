package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class DemoObservableCreateDisposing {

    public static void main(String[] args) {

        Observable<Boolean> puertaAbierta = Observable.create(emitter -> {

            Thread t = new Thread(() -> {
                while (!emitter.isDisposed()) {
                    boolean abrir = Math.random() >= 0.5;
                    emitter.onNext(abrir);
                    try {
                        Thread.sleep((int)(Math.random() * 1000) + 1000);
                    } catch(Throwable e) {
                        emitter.onError(e);
                    }
                }

                emitter.onComplete();
            });

            t.start();

        });

        Disposable observadorPuerta = puertaAbierta.subscribe(abierta -> System.out.println(abierta ? "PUERTA ABIERTA" : "PUERTA CERRADA"));

        try {
            Thread.sleep(5000);
            System.out.println("Han pasado 5 segundos");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        observadorPuerta.dispose();

        try {
            Thread.sleep(5000);
            System.out.println("Han pasado 5 segundos");
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
