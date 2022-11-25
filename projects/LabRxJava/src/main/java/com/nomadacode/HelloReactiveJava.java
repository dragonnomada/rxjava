package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class HelloReactiveJava {

    public static void main(String[] args) {

        HelloProducto producto1 = new HelloLibro("Ana Frank", "Mi lucha", 1945, 1.99, 123);
        HelloProducto producto2 = new HelloRefresco("Pascual", "Boing de Fresa", 355, 15, 23);
        HelloProducto producto3 = new HelloLibro("Hitler", "Neonazi", 1939, 2.99, 456);
        HelloProducto producto4 = new HelloRefresco("Chaprrita", "Fresa", 355, 12.3, 45);

        Observable<HelloProducto> productos = Observable.just(producto1, producto2, producto3, producto4);

        System.out.println("Productos:");
        productos.subscribe(producto -> producto.describir());

        System.out.println("Primer producto:");
        productos.first(producto1).subscribe(producto -> producto.describir());

        System.out.println("Productos que cuestan más de $5.00:");
        productos.filter(producto -> producto.getPrecio() >= 5.0)
                .subscribe(producto -> producto.describir());

    }

}
