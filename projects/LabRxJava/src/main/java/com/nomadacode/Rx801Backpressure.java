package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Rx801Backpressure {

    public static void main(String[] args) {

        class Product {
            long id;

            public Product(long id) {
                this.id = id;
                System.out.printf("Producto %d generado %n", id);
            }

            @Override
            public String toString() {
                return String.format("Soy el producto: %d", id);
            }
        }

        Observable.interval(10, TimeUnit.MILLISECONDS)
                .map(Product::new)
                .observeOn(Schedulers.io())
                .subscribe(product -> {
                    Sleep.sleep(1000);
                    System.out.printf("PRODUCTO PROCESADO: %s %n", product);
                });

        Sleep.sleep(10000);

    }

}
