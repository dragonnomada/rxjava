package org.example;

public class TestListProductos {

    public static void main(String[] args) {

        ListProductosObservable productos = new ListProductosObservable();

        productos.addProducto("A", 12);
        productos.addProducto("B", 99);
        productos.addProducto("C", -1);
        productos.addProducto("D", 103);
        productos.addProducto("X", 0);

        productos.describe();

        productos.getProductosPorPrecio(80, 120)
                .subscribe(producto -> System.out.println(producto.getPrecio()));

    }

}
