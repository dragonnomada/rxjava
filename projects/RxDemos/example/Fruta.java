package org.example;

public class Fruta {

    private String nombre;
    private double precio;
    private double cantidad;

    // ... constructor / getters / setters

    public Fruta(String nombre, double precio, double cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return String.format("Fruta: %s $%.2f (%.2f kg)", nombre, precio, cantidad);
    }

}
