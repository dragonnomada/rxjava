package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoErrorSuppressing {

    public static void main(String[] args) {

        Observable<String> nombres = Observable.create(emitter -> {

            String[] nicknames = new String[]{"pepe", "paco", "ana", "bety"};

            while (!emitter.isDisposed()) {
                int value = (int) (Math.random() * 100 + 1);
                String nickname = nicknames[(int) Math.floor(Math.random() * nicknames.length)];
                String nombre = String.format("%s%d", nickname, value);
                emitter.onNext(nombre);
                Sleep.sleep(1000);
            }

            emitter.onComplete();

        });

        // Hacer un mapeo sobre un observador que controle el error y lo suprima

        // .map(i -> j)
        // .flatMap(i -> Observable)

        //.map(nombre -> {
        //    int value = Integer.parseInt(nombre.replaceAll("[a-z]+", ""));
        //    if (value > 90) {
        //        throw new Exception("El ninckname generado no es válido: " + nombre);
        //    }
        //    return nombre;
        //})

        nombres.flatMap(nombre -> {
                    int value = Integer.parseInt(nombre.replaceAll("[a-z]+", ""));
                    if (value > 90) {
                        return Observable.empty();
                    }
                    return Observable.just(nombre);
                })
                .subscribe(nombre -> System.out.println(nombre));

    }

}
