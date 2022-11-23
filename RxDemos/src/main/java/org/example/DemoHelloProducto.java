package org.example;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;

class Producto {

    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

public class DemoHelloProducto {

    public static void main(String[] args) {

        Observable<Producto> stock = Observable.just(
                new Producto("Coca Cola", 15.99),
                new Producto("Pepsi", 14.99),
                new Producto("Galletas Marías", 13.57),
                new Producto("Gansito", 10.55)
        );

        stock.subscribe(producto -> System.out.println(producto));

        Observable<String> nombres = stock.map(producto -> producto.getNombre());

        Observable<Double> precios = stock.map(producto -> producto.getPrecio());

        Maybe<Double> total = precios.reduce((suma, precio) -> suma + precio);

        nombres.subscribe(nombre -> System.out.println(nombre));

        precios.subscribe(precio -> System.out.printf("$%.2f %n", precio));

        total.subscribe(valor -> System.out.printf("Total: $%.2f %n", valor));

    }

}
