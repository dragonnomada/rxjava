package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DemoObservableMerge {

    public static void main(String[] args) {

        // Factory: Observable.merge(obs1, obs2, ...)

        Observable<String> obs1 = Observable.interval(200, TimeUnit.MILLISECONDS)
                        .map(i -> String.format("[1: %d]", i));

        Observable<String> obs2 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> String.format("[2: %d]", i));

        Observable<String> obs3 = Observable.interval(100, TimeUnit.MILLISECONDS)
                .map(i -> String.format("[3: %d]", i));

        Observable<String> feed = Observable.merge(obs1, obs2, obs3);

        feed.subscribe(message -> System.out.println(message));

        Sleep.sleep(2000);

    }

}
