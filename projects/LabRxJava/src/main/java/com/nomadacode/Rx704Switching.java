package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Rx704Switching {

    public static void main(String[] args) {

        Observable<Timed<Integer>> feed = Observable.range(1, 100)
                .subscribeOn(Schedulers.computation())
                .concatMap(i ->
                        Observable.just(i).delay((long) (Math.random() * 3000), TimeUnit.MILLISECONDS)
                )
                .timeInterval();

        //source.blockingSubscribe(System.out::println);

        Observable.interval(5, TimeUnit.SECONDS)
                .switchMap(i -> feed.doOnDispose(() -> System.out.println("LIBERADO")))
                .blockingSubscribe(System.out::println);

    }

}
