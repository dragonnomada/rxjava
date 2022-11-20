package com.nomadacode;

public class Rx105GreetingLambda {

    public static void main(String[] args) {

        Rx105Greeting myGreeting = message -> System.out.printf("Tu saludo dice: %s %n", message);

        myGreeting.greet("Hola mundo");

    }

}
