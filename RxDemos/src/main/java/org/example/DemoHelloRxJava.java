package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoHelloRxJava {

    public static void main(String[] args) {

        // Construcción del observable (el que dice que observar)
        Observable<Integer> numeros = Observable.just(20, 23, 50, 32);

        Observable<Double> raices = numeros.map(valor -> Math.sqrt(valor));

        // El consumo del observable,
        // le llamamos el observador (el que observa)
        numeros.subscribe(valor -> System.out.printf("El número es: %d %n", valor));

        raices.subscribe(valor -> System.out.printf("La raíz es: %.4f %n", valor));

    }

}
