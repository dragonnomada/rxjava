package org.example;

public class TestTiendaFrutas {

    public static void main(String[] args) {

        TiendaFruta tienda = new TiendaFruta();

        tienda.registrarFruta("manzana", 15.99);
        tienda.registrarFruta("plátano", 25.99);
        tienda.registrarFruta("pera", 35.99);
        tienda.registrarFruta("mango", 45.99);
        tienda.registrarFruta("kiwi", 55.99);
        tienda.registrarFruta("guanabana brasileña", 55.99);

        tienda.registrarCantidadFruta("manzana", 13);
        tienda.registrarCantidadFruta("mango", 8);
        tienda.registrarCantidadFruta("plátano", 2);
        tienda.registrarCantidadFruta("pera", 1.5);
        tienda.registrarCantidadFruta("guanabana brasileña", 1.5);

        tienda.reporteFrutas();

    }

}
