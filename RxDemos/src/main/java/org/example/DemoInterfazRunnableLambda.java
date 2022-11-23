package org.example;

public class DemoInterfazRunnableLambda {

    public static void main(String[] args) {

        Runnable runnable = () -> System.out.println("Hola mundo");

        runnable.run();
        runnable.run();
        runnable.run();

    }

}
