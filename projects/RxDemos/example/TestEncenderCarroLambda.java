package org.example;

public class TestEncenderCarroLambda {

    public static void main(String[] args) {

        IEncenderCarro encendedorToyota = (String llave) -> {
            if (llave.startsWith("toyota:")) {
                System.out.println("Encendió");
            } else {
                System.out.println("No arranca");
            }
        };

        encendedorToyota.encender("aveo:123");
        encendedorToyota.encender("toyota:456");

    }

}
