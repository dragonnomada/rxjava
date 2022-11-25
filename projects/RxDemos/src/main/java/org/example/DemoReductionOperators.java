package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class DemoReductionOperators {

    public static void main(String[] args) {

        System.out.println("count()");

        Observable<Double> precios = Observable.just(13.55, 19.99, 14.77, 12.88);

        precios.count()
                .subscribe(conteo -> System.out.printf("Conteo precios: %d %n", conteo));

        Observable<Long> sourceInterval = Observable.interval(1, TimeUnit.SECONDS)
                .take(3);

        sourceInterval.count()
                .subscribe(conteo -> System.out.printf("Conteo intervalo: %d %n", conteo));

        Sleep.sleep(4000);

        System.out.println("reduce()");

        /*
            total <- 0
            total <- total + precio(1)
            total <- total + precio(2)
            total <- total + precio(3)
         */

        precios.reduce(0.0, (total, precio) -> total + precio)
                .subscribe(total -> System.out.printf("Total precios: $%.2f %n", total));

        precios.reduce("", (text, precio) -> text + String.format("$%.2f, ", precio))
                .subscribe(formato -> System.out.println(formato));

        precios.reduce(new ArrayList<Double>(), (list, precio) -> {
            list.add(precio);
            return list;
        }).subscribe(list -> System.out.println(list));

    }

}
