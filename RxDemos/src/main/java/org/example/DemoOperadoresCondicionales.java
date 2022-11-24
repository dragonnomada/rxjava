package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

class Sleep {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

public class DemoOperadoresCondicionales {

    static void test1() {
        Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> i * i)
                .takeWhile(j -> j < 10)
                .subscribe(System.out::println);

        Sleep.sleep(5000);
    }

    static void test2() {
        Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> i * i)
                .skipWhile(j -> j < 10)
                .subscribe(System.out::println);

        Sleep.sleep(7000);
    }

    static void test3() {
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .takeUntil(Observable.interval(1, TimeUnit.SECONDS))
                .subscribe(System.out::println);

        Sleep.sleep(3000);
    }

    static void test4() {
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .skipUntil(Observable.interval(2, TimeUnit.SECONDS))
                .subscribe(System.out::println);

        Sleep.sleep(3000);
    }

    static void test5() {
        Observable.range(1, 20)
                .filter(i -> i > 100)
                .defaultIfEmpty(0)
                .filter(j -> j > 0)
                .defaultIfEmpty(1)
                .subscribe(System.out::println);
    }

    static void test6() {
        Observable.range(1, 20)
                .filter(i -> i > 100)
                .switchIfEmpty(Observable.just(100, 101, 102))
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {

        test6();

    }

}
