package org.example;

public class DemoInterfazRunnableClasico {

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hola mundo");
            }
        };

        runnable.run();
        runnable.run();
        runnable.run();

    }

}
