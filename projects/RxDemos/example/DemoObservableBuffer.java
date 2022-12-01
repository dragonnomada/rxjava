package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoObservableBuffer {

    public static void main(String[] args) {

        Observable.range(1, 20)
                .buffer(5, 6)
                .subscribe(System.out::println);

        System.out.println("-----------------------");

        Observable.range(1, 20)
                .buffer(5, 4)
                .subscribe(System.out::println);

        System.out.println("-----------------------");

        Observable.range(1, 20)
                .buffer(6)
                .subscribe(System.out::println);

    }

}
