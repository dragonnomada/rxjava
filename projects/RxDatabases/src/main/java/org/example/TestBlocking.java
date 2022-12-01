package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.sql.ClientInfoStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBlocking {

    @Test
    public void testBlocking() {

        System.out.println(Thread.currentThread().getName());

        System.out.printf("A %s %n", LocalDateTime.now());

        List<Long> source = Observable.interval(1, TimeUnit.MICROSECONDS)
                .take(5_000_000)
                .toList()
                .blockingGet();

        System.out.printf("B (Size: %d) %s %n", source.size(), LocalDateTime.now());

        System.out.printf("C %s %n", LocalDateTime.now());

    }

    @Test
    public void testBlockingWithThread() throws InterruptedException {

        System.out.println(Thread.currentThread().getName());

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());

            System.out.printf("A %s %n", LocalDateTime.now());

            List<Long> source = Observable.interval(1, TimeUnit.MICROSECONDS)
                    .take(5_000_000)
                    .toList()
                    .blockingGet();

            System.out.printf("B (Size: %d) %s %n", source.size(), LocalDateTime.now());
        });

        thread.start();

        System.out.printf("C %s %n", LocalDateTime.now());

        thread.join();

        System.out.printf("D %s %n", LocalDateTime.now());

    }

    @Test
    public void testAsync() throws InterruptedException {

        Single<String> sourceResult = Observable.fromCallable(() -> {
                    System.out.println(Thread.currentThread().getName());
                    System.out.printf("A %s %n", LocalDateTime.now());
                    return "ok";
                })
                .subscribeOn(Schedulers.io())
                .switchMap(s -> Observable.interval(1, TimeUnit.MICROSECONDS)
                        .take(5_000_000)
                )
                .toList()
                .map(source -> {
                    System.out.println(Thread.currentThread().getName());
                    System.out.printf("B (Size: %d) %s %n", source.size(), LocalDateTime.now());
                    return "ok 2";
                });

        System.out.printf("C %s %n", LocalDateTime.now());

        String result = Observable.wrap(sourceResult.toObservable())
                .blockingFirst();

        System.out.printf("D [%s] %s %n", result, LocalDateTime.now());

    }

}
