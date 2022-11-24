package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;

class PagoFacil implements Action {

    boolean pagado = false;

    @Override
    public void run() throws Throwable {
        System.out.println("Realizando pago...");
        //
        if (Math.random() > 0.2) {
            pagado = true;
            System.out.println("Pago exitoso");
        } else {
            pagado = false;
            System.out.println("Pago fallido");
        }
    }

    boolean pagar() {
        try {
            this.run();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}

public class DemoMaybe {

    public static void main(String[] args) {

        Observable.just(1, 2, 3)
                .elementAt(4)
                .subscribe(
                        i -> System.out.printf("El elemento de índice 4 es: %d %n", i),
                        Throwable::printStackTrace,
                        () -> System.out.println("No existe el elemento de índice 4")
                );

        Observable.just(1, 2, 3, 4, 5, 6)
                .elementAt(4)
                .subscribe(
                        i -> System.out.printf("El elemento de índice 4 es: %d %n", i),
                        Throwable::printStackTrace,
                        () -> System.out.println("No existe el elemento de índice 4")
                );

        Observable.just("manzana", "pera", "kiwi", "piña")
                .filter(fruta -> fruta.startsWith("x"))
                .firstElement()
                .subscribe(
                        fruta -> System.out.printf("Existe la fruta: %s %n", fruta),
                        Throwable::printStackTrace,
                        () -> System.out.println("No existe la fruta :(")
                );

        CompletableObserver completador = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("Proceso completado");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        };

        Completable completable = Completable.complete();

        // ...

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        completable.subscribe(completador);

        Action boton = () -> System.out.println("Botón pulsado");

        Completable completable1 = Completable.fromAction(boton);

        completable1.subscribe(() -> System.out.println("El botón ha sido pulsado"));

        try {
            boton.run();
            boton.run();
            boton.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        PagoFacil pagoFacil = new PagoFacil();

        Completable completable2 = Completable.fromAction(pagoFacil);

        completable2.subscribe(() -> System.out.println("Observador 1 sobre PAGO"));
        completable2.subscribe(() -> System.out.println("Observador 2 sobre PAGO"));

        pagoFacil.pagar();
        pagoFacil.pagar();
        pagoFacil.pagar();
        pagoFacil.pagar();

        Observable<PagoFacil> observable = Observable.fromAction(pagoFacil);

        observable.subscribe(x -> System.out.println("Observador 1 sobre PAGO"));
        observable.subscribe(x -> System.out.println("Observador 2 sobre PAGO"));

        pagoFacil.pagar();
        pagoFacil.pagar();
        pagoFacil.pagar();
        pagoFacil.pagar();

    }

}
