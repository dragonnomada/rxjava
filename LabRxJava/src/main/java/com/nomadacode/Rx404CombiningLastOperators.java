package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Rx404CombiningLastOperators {

    public static void main(String[] args) {

        Disposable disposable = Observable.combineLatest(
                Observable.interval(1200, TimeUnit.MILLISECONDS).take(3),
                Observable.interval(600, TimeUnit.MILLISECONDS).take(6),
                Observable.interval(300, TimeUnit.MILLISECONDS).take(12),
                (s1, s2, s3) -> String.format("[%d | %d | %d]", s1, s2, s3)
        ).subscribe(System.out::println);

        Sleep.sleep(4000);

        disposable.dispose();

        System.out.println("---------------------------------");

        Observable.interval(1200, TimeUnit.MILLISECONDS)
                .take(3)
                .withLatestFrom(
                        Observable.interval(600, TimeUnit.MILLISECONDS).take(6),
                        Observable.interval(300, TimeUnit.MILLISECONDS).take(12),
                        (s1, s2, s3) -> String.format("[%d | %d | %d]", s1, s2, s3)
                ).subscribe(System.out::println);

        Sleep.sleep(4000);

    }

}
