package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;

public class ListProductos {

    protected List<Producto> productos = new ArrayList<>();

    public void addProducto(Producto producto) {
        if (producto.getPrecio() > 0) {
            productos.add(producto);
        }
    }

    public void addProducto(String nombre, double precio) {
        this.addProducto(new Producto(nombre, precio));
    }

    public Producto getProducto(int index) {
        return productos.get(index);
    }
}
