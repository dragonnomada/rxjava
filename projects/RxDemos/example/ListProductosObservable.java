package org.example;

import io.reactivex.rxjava3.core.Observable;

public class ListProductosObservable extends ListProductos {

    private Observable<Producto> productoObservable;

    public ListProductosObservable() {
        super();
        productoObservable = Observable.fromIterable(productos);
    }

    public Observable<Producto> getProductoObservable() {
        return productoObservable;
    }

    public Observable<Producto> getProductosPorPrecio(double precioMin, double precioMax) {
        return productoObservable.filter(producto -> producto.getPrecio() >= precioMin && producto.getPrecio() <= precioMax);
    }

    public void describe() {
        productoObservable.map(producto -> String.format("[$%.2f] %s", producto.getPrecio(), producto.getNombre()))
                .reduce((text, formato) -> text + "\n" + formato)
                .subscribe(descripcion -> System.out.println(descripcion));
    }

}
