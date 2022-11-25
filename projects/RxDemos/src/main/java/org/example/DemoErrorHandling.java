package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DemoErrorHandling {

    public static void main(String[] args) {

        Observable<String> nombres = Observable.create(emitter -> {

            String[] nicknames = new String[]{"pepe", "paco", "ana", "bety"};

            while (!emitter.isDisposed()) {
                int value = (int) (Math.random() * 100 + 1);
                String nickname = nicknames[(int) Math.floor(Math.random() * nicknames.length)];
                String nombre = String.format("%s%d", nickname, value);
                if (value > 90) {
                    emitter.onError(new Exception("El ninckname generado no es válido: " + nombre));
                    continue;
                }
                emitter.onNext(nombre);
                Sleep.sleep(1000);
            }

            emitter.onComplete();

        });

        nombres.onErrorReturn(throwable -> {
                    int value = (int) (Math.random() * 100 + 1);
                    return String.format("anonymous%d", value);
                })
                .subscribe(nombre -> System.out.println(nombre));

        System.out.println("---------------------------------------");

        nombres.onErrorReturnItem("EXAMPLE")
                .subscribe(nombre -> System.out.println(nombre));

        System.out.println("---------------------------------------");

        nombres.onErrorResumeWith(Observable.just("ERROR", "123", "456"))
                .subscribe(nombre -> System.out.println(nombre));

        System.out.println("---------------------------------------");

        nombres.onErrorResumeNext(throwable -> {
                    return Observable.create(emitter -> {
                        for (int i = 0; i < 3; i++) {
                            int value = (int) (Math.random() * 100 + 1);
                            String nombre = String.format("anonymous%d", value);
                            emitter.onNext(nombre);
                        }
                        emitter.onComplete();
                    });
                })
                .subscribe(nombre -> System.out.println(nombre));

        nombres.retry(3)
                .subscribe(nombre -> System.out.println(nombre));

        Sleep.sleep(10000);

    }

}
