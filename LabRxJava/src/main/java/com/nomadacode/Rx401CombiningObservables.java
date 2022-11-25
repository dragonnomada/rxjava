package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

class Sleep {
    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

public class Rx401CombiningObservables {

    public static void main(String[] args) {

        Observable.merge(
                Observable.just("A", "B", "C"),
                Observable.interval(1, TimeUnit.SECONDS)
                        .take(3)
                        .map(i -> String.format("[%d]", i)),
                Observable.just("D", "E", "F")
        ).subscribe(System.out::println);

        Sleep.sleep(4000);

        Observable.concat(
                Observable.just("A", "B", "C"),
                Observable.interval(1, TimeUnit.SECONDS)
                        .take(3)
                        .map(i -> String.format("[%d]", i)),
                Observable.just("D", "E", "F")
        ).subscribe(System.out::println);

        Sleep.sleep(4000);

    }

}
