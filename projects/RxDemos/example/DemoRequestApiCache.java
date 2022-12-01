package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DemoRequestApiCache {

    public static void main(String[] args) {

        Observable<String> sourceApiCache = Observable.interval(2, TimeUnit.SECONDS)
                .flatMap(i -> RandomUserRequestObservable.request())
                .take(10)
                .cache();

        sourceApiCache.blockingSubscribe(
                s -> {},
                Throwable::printStackTrace,
                () -> {

                    sourceApiCache.subscribe(result -> System.out.printf("<<1*>> %s %n", result));
                    sourceApiCache.subscribe(result -> System.out.printf("<<2*>> %s %n", result));
                    sourceApiCache.subscribe(result -> System.out.printf("<<3*>> %s %n", result));
                    sourceApiCache.subscribe(result -> System.out.printf("<<4*>> %s %n", result));

                }
        );

    }

}
