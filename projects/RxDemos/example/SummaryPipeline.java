package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;

import java.util.ArrayList;
import java.util.List;

public class SummaryPipeline {

    public static void main(String[] args) throws Throwable {

        // Pipeline
        // 1. Fuente de datos/eventos
        List<Producto> productos = new ArrayList<>();

        productos.add(new Producto("A", 12));
        productos.add(new Producto("B", 15));
        productos.add(new Producto("C", 17));

        // Frío <-> Cálido
        Observable<Producto> observableProductos = Observable.fromIterable(productos);

        // 2. Operadores -> Transformación y reducción

        Observable<String> observableProductoNombres = observableProductos
                .map(producto -> producto.getNombre());

        Observable<Producto> observableProductosDescuento = observableProductos
                .filter(producto -> producto.getPrecio() <= 15);

        Observable<Double> observableProductosDescuentoPrecioIva = observableProductosDescuento
                .map(producto -> producto.getPrecio() * 1.16);

        // 3. Procesar los resultados

        observableProductos.map(producto -> String.format("[$%.2f] %s", producto.getPrecio(), producto.getNombre()))
                .subscribe(formato -> System.out.println(formato));

        System.out.println("----------------------------------");

        observableProductosDescuento.map(producto -> String.format("[$%.2f] %s", producto.getPrecio(), producto.getNombre()))
                .subscribe(formato -> System.out.println(formato));

    }

}
