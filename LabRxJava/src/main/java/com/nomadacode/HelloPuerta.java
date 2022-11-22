package com.nomadacode;

public class HelloPuerta extends HelloBufferSum {

    int i = 0;

    void ingresar(int x) throws Exception {
        if (i >= this.valores.length) {
            throw new Exception("No pueden ingresar más por la puerta");
        }

        this.ajustar(i, x);
        i++;
    }

    void salir() {
        i--;
        this.ajustar(i, 0);
    }

    public static void main(String[] args) {

        HelloPuerta puerta = new HelloPuerta();

        System.out.println(puerta.sumar());

        try {
            puerta.ingresar(25);
            puerta.ingresar(17);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(puerta.sumar());

        puerta.salir();

        System.out.println(puerta.sumar());

    }

}
