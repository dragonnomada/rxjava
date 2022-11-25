package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DemoObservableConcat {

    public static void main(String[] args) {

        // Factory: Observable.concat(obs1, obs2, ...)

        Observable<String> obs1 = Observable.interval(200, TimeUnit.MILLISECONDS)
                .map(i -> String.format("[1: %.1f]", Math.random() * 10))
                .take(2);

        Observable<String> obs2 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> String.format("[2: %.1f]", Math.random() * 10))
                .take(2);

        Observable<String> obs3 = Observable.interval(100, TimeUnit.MILLISECONDS)
                .map(i -> String.format("[3: %.1f]", Math.random() * 10))
                .take(2);

        Observable<String> feed = Observable.concat(obs1, obs2, obs3);

        feed.repeat(4).subscribe(message -> System.out.println(message));

        Sleep.sleep(20000);

    }

}
