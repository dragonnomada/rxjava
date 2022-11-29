package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoWindow {

    public static void main(String[] args) {

        Observable.range(1, 100)
                .window(5)
                .flatMapSingle(observable ->
                        observable.reduce("", (text, i) -> String.format("%s + %d", text, i))
                )
                .subscribe(System.out::println);

    }

}
