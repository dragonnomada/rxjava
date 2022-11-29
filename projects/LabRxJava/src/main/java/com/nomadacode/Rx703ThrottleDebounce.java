package com.nomadacode;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.concurrent.TimeUnit;

public class Rx703ThrottleDebounce {

    public static void main(String[] args) {

        Subject<Long> timer = PublishSubject.create();

        timer.timeInterval()
                .throttleWithTimeout(2, TimeUnit.SECONDS)
                .subscribe(System.out::println);

        Thread t = new Thread(() -> {
            long count = 0;
            while (true) {
                timer.onNext(count++);
                Sleep.sleep((long) (Math.random() * 3000));
            }
        });

        t.start();

    }

}
