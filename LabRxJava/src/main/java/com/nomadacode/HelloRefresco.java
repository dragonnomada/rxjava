package com.nomadacode;

public class Refresco {

    String marca;
    String tipo;
    int mililitros;
    double precio;
    int existencias;

    Refresco(String marca, String tipo, int mililitros, double precio, int existencias) {
        this.marca = marca;
        this.tipo = tipo;
        this.mililitros = mililitros;
        this.precio = precio;
        this.existencias = existencias;
    }

    void describir() {
        System.out.printf("[$%.2f] %s / %s %d ml (%d) %n", precio, marca, tipo, mililitros, existencias);
    }

    public static void main(String[] args) {

        Refresco refresco1 = new Refresco("Coca Cola", "Fanta", 600, 13.5, 241);

        refresco1.describir();

        Refresco refresco2 = new Refresco("Pepsi", "7Up", 1000, 24.5, 33);

        refresco2.describir();

    }

}
