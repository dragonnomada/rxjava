package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoObservableThreadShareValue {

    public static void main(String[] args) {

        AtomicInteger contador = new AtomicInteger();

        Producto producto = new Producto("Coca", 12.5);

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.from(Executors.newFixedThreadPool(15)))
                .doOnNext(i -> {
                    int a = contador.incrementAndGet();
                    System.out.printf("[1]: INC %d %d %n", a, contador.get());
                    producto.setPrecio(producto.getPrecio() + 1.0);
                })
                .subscribe(i -> {
                    System.out.printf("[1]: %d %n", contador.get());
                    System.out.println(producto);
                    System.out.println(Thread.currentThread().getName());
                });

        Observable.interval(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext(i -> {
                    int a = contador.incrementAndGet();
                    System.out.printf("[2]: INC %d %d %n", a, contador.get());
                    producto.setPrecio(producto.getPrecio() + 1.5);
                })
                .subscribe(i -> {
                    System.out.printf("[2]: %d %n", contador.get());
                    System.out.println(producto);
                    System.out.println(Thread.currentThread().getName());
                });

        Sleep.sleep(10000);

    }

}
