package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.observers.DisposableObserver;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ProductoOperators {

    public static ObservableTransformer<Producto, String> productoNombreMasCaro() {
        return new ObservableTransformer<Producto, String>() {
            @Override
            public @NonNull ObservableSource<String> apply(@NonNull Observable<Producto> observableOriginal) {
                return observableOriginal
                        .sorted((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()))
                        .lastElement()
                        .map(producto -> producto.getNombre())
                        .toObservable();
            }
        };
    }

    public static ObservableTransformer<Double, Producto> generarProductosPorPrecio() {
        return observablePrecios -> {
            AtomicInteger contador = new AtomicInteger(1);
            return observablePrecios.map(precio -> {
                String nombre = String.format("Producto %d", contador.getAndIncrement());
                return new Producto(nombre, precio);
            });
        };
    }

}

public class DemoCustomOperators {

    public static void main(String[] args) {

        Observable<String> sourceNombres = Observable.just("Coca", "Pepsi", "Galletas Marías");
        Observable<Double> sourcePrecios = Observable.just(13.5, 12.99, 9.84);

        Observable<Producto> sourceProductos = Observable.zip(
                sourceNombres,
                sourcePrecios,
                Producto::new
                //(nombre, precio) -> new Producto(nombre, precio)
        );


        sourceProductos.sorted((pA, pB) -> Double.compare(pA.getPrecio(), pB.getPrecio()))
                .lastElement()
                .map(producto -> producto.getNombre())
                .subscribe(nombreMasCaro -> System.out.println(nombreMasCaro));
                //.reduce(0.0, (total, producto) -> total + producto.getPrecio())
                //.subscribe(total -> System.out.println(total));

        sourceProductos.compose(ProductoOperators.productoNombreMasCaro())
                .subscribe(nombre -> System.out.println(nombre));

        Observable.interval(10, TimeUnit.MILLISECONDS)
                .map(i -> new Producto(String.format("Producto %d", i), Math.random() * 100))
                .take(50)
                .compose(ProductoOperators.productoNombreMasCaro())
                .blockingSubscribe(System.out::println);

        Observable<Double> precios = Observable.just(11.56, 12.77, 19.87, 13.42, 10.5);

        precios.compose(ProductoOperators.generarProductosPorPrecio())
                .subscribe(System.out::println);

        Observable.range(1, 100)
                .map(i -> Math.random() * 1_000.0)
                .compose(ProductoOperators.generarProductosPorPrecio())
                .subscribe(System.out::println);
    }

}
