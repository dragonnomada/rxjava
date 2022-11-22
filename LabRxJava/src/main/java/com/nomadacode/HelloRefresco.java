package com.nomadacode;

public class HelloRefresco implements HelloProducto {

    String marca;
    String tipo;
    int mililitros;
    double precio;
    int existencias;

    HelloRefresco(String marca, String tipo, int mililitros, double precio, int existencias) {
        this.marca = marca;
        this.tipo = tipo;
        this.mililitros = mililitros;
        this.precio = precio;
        this.existencias = existencias;
    }

    @Override
    public String getNombre() {
        return String.format("%s / %s %d ml", marca, tipo, mililitros);
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public int getExistencias() {
        // Ir a la base de datos y recuperar las existencias
        return existencias;
    }

    public void describir() {
        System.out.printf("[$%.2f] %s / %s %d ml (%d) %n", precio, marca, tipo, mililitros, existencias);
    }

    public static void main(String[] args) {

        HelloRefresco refresco1 = new HelloRefresco("Coca Cola", "Fanta", 600, 13.5, 241);

        refresco1.describir();

        HelloRefresco refresco2 = new HelloRefresco("Pepsi", "7Up", 1000, 24.5, 33);

        refresco2.describir();

    }

}
