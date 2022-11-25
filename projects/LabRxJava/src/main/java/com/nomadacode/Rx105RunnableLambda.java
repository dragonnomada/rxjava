package com.nomadacode;

public class Rx105RunnableLambda {

    public static void main(String[] args) {

        Runnable runnable = () -> System.out.println("Hola mundo :D");

        runnable.run();

    }

}
