package com.nomadacode;

public class HelloMul {

    // Función: multiplica(int, int) -> int
    static int multiplica(int a, int b) {
        return a * b;
    }

    static void reportaMultiplicacion(int a, int b, int c) {
        System.out.printf("%d * %d = %d %n", a, b, c);
    }

    public static void main(String[] args) {

        int c = multiplica(123, 456);

        reportaMultiplicacion(123, 456, c);

        reportaMultiplicacion(
                35,
                47,
                multiplica(35, 47)
        );

        reportaMultiplicacion(
                32,
                23,
                multiplica(32, 23)
        );

    }

}
