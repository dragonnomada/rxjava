package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoPredicadoProducto {

    public static void main(String[] args) {

        Producto b = new Producto("B", 28);

        Observable<Producto> productoObservable = Observable.just(
                new Producto("A", 1),
                b,
                new Producto("C", 13),
                new Producto("D", 25),
                new Producto("E", 3)
        );

        productoObservable.any(producto -> producto.getNombre().equals("B"))
                .subscribe(resultado -> {
                    if (resultado) {
                        System.out.println("Si está B");
                        // TODO:
                    } else {
                        System.out.println("No está B");
                    }
                });

        productoObservable.contains(b)
                .subscribe(resultado -> System.out.println(resultado ? "CONTIENE A B" : "NO CONTIENE A B"));

    }

}
