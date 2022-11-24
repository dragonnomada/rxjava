package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class DemoSingle {

    public static void main(String[] args) {

        Thread thread = new Thread();

        Single<Thread> source = Single.just(thread);

        source.subscribe(thread1 -> System.out.println(thread1.isAlive()));

        Observable<Integer> rango = Observable.range(1, 3000);

        rango.first(1).subscribe(System.out::println);
        rango.last(1).subscribe(System.out::println);

    }

}
