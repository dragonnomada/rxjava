package com.nomadacode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Rx204Observer {

    public static void main(String[] args) {

        Observer<String> observer1 = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                // Al suscribir al observador, este método será llamado
                System.out.println("Inicio de la observación :O");
            }

            @Override
            public void onNext(@NonNull String s) {
                // Observamos el próximo *elemento T* y hacemos algo con él
                System.out.printf("Recibido: %s %n", s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                // En caso de error recibimos el error en forma de interfaz Throwable y hacemos algo con él
                System.out.printf("ERROR: %s %n", e.getMessage());
            }

            @Override
            public void onComplete() {
                // Una vez completado el pipeline, hacemos algo al final
                System.out.println("Fin del Observable :D");
            }
        };

        Observable<String> observable1 = Observable.just("Ana", "Beto", "Carla", "Eduardo");

        observable1.subscribe(observer1);

    }

}
