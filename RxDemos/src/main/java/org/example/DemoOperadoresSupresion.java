package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoOperadoresSupresion {

    static void test1() {

        Observable.range(1, 1000)
                .filter(i -> i % 41 == 0 || i % 71 == 0 || i % 87 == 0)
                .filter(j -> j % 10 == 1)
                .subscribe(System.out::println);

    }

    static void test2() {

        Observable.range(1, 1000)
                .take(10)
                .subscribe(System.out::println);

    }

    static void test3() {

        int pageCount = 54;
        int pageSize = 5;

        Observable.range(1, 1000)
                .skip(pageCount * pageSize)
                .take(pageSize)
                .subscribe(System.out::println);

    }

    static void test4() {

        Observable.just("juan perez", "juan diaz", "jorge gomez", "jorge falcon")
                .map(nombre -> nombre.replaceAll("\\s\\w+", ""))
                .distinct()
                .subscribe(System.out::println);

        Observable.just("juan perez", "juan diaz", "jorge gomez", "jorge falcon")
                .distinct(nombre -> nombre.replaceAll("\\s\\w+", ""))
                .subscribe(System.out::println);

    }

    static void test5() {

        Observable.just(1, 0, 1, 1, 2, 1, 1, 0, 0, 1)
                .distinctUntilChanged() // suprime a los repetidos siguientes
                .subscribe(System.out::println);

    }

    static void test6() {

        Observable.range(10, 100)
                .elementAt(4) // índice 4 -> elemento 14
                .subscribe(System.out::println);

    }

    public static void main(String[] args) {

        test6();

    }

}
