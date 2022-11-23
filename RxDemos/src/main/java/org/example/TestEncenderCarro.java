package org.example;

public class TestEncenderCarro {

    public static void main(String[] args) {

        EncenderCarro encendedorChevy = new EncenderCarro() {
            @Override
            public void encender(String llave) {
                if (llave.startsWith("chevy:")) {
                    System.out.println("El carro prendió");
                } else {
                    System.out.println("La llave no funciona");
                }
            }
        };

        encendedorChevy.encender("aveo:123");
        encendedorChevy.encender("chevy:123");

    }

}
