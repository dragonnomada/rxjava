package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class RxE1002ProductoLambda {

    public static void main(String[] args) {

        RxE1002Precio precioLibro = () -> 10.99;
        RxE1002Precio precioRefresco = () -> 18.95;
        RxE1002Precio precioGalletas = () -> 11.73;

        Observable<RxE1002Precio> productos = Observable.just(precioLibro, precioGalletas, precioRefresco);

        productos.subscribe(producto -> System.out.println(producto.getPrecio()));

        Observable<Double> precios = productos.map(producto -> producto.getPrecio());

        precios.subscribe(precio -> System.out.printf("$%.2f %n", precio));

    }

}
