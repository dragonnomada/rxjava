package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

class Greeting {

    public static String greet(String message) {
        return String.format("Hola %s", message);
    }

}

class Product {

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double priceWithTax(double tax) {
        return price * (1 + tax);
    }

}

public class Rx202MethodReference {

    public static void main(String[] args) {

        Observable<String> fruits = Observable.just("Manzana", "Mango", "Pera", "Piña");

        Observable<String> fruitGreetings = fruits.map(Greeting::greet);

        fruitGreetings.subscribe(System.out::println);

        Observable<Integer> fruitLengths = fruits.map(String::length);

        fruitLengths.subscribe(System.out::println);

        Product coca = new Product("Coca Cola", 12.5);

        Observable<Double> taxes = Observable.just(.16, .10, .10667);

        Observable<Double> pricesWithTaxes = taxes.map(coca::priceWithTax);

        pricesWithTaxes.subscribe(System.out::println);

    }

}
