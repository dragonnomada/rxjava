package com.nomadacode;

public class Rx105RunnableClassic {

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hola mundo :D");
            }
        };

        runnable.run();

    }

}
