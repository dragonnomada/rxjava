package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Rx1002BlockingOperators {

    @Test
    public void testBlockingFirst() {

        int result = Observable.range(1, 100)
                .filter(i -> i % 7 == 0 && i % 13 == 0)
                .blockingFirst();

        Assert.assertEquals(7 * 13, result);

    }

    @Test
    public void testBlockingGet() {

        int result = Observable.range(1, 100)
                .reduce((s, i) -> s + i)
                .blockingGet();

        Assert.assertEquals(5050, result);

    }

    @Test
    public void testBlockingLast() {

        int result = Observable.range(1, 100)
                .filter(i -> i % 5 == 0 && i % 3 == 0)
                .blockingLast();

        Assert.assertEquals(3 * 5, result);

    }

    @Test
    public void testBlockingIterable() {

        Iterable<Integer> odds = Observable.range(1, 100)
                .filter(i -> (i & 2) == 1)
                .blockingIterable();

        for (int n : odds) {
            Assert.assertEquals(1, n % 2);
        }

    }

    @Test
    public void testBlockingForEach() {

        Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
                .map(i -> 2 * i)
                .forEach(j ->
                        Assert.assertEquals(0, j % 2 == 0)
                );

    }

    @Test
    public void testBlockingNext() {

        Iterable<Long> iterable = Observable.interval(10, TimeUnit.MILLISECONDS)
                .take(100).blockingNext();

        for (long n : iterable) {
            Sleep.sleep(100);
            System.out.println(n);
        }

    }

    @Test
    public void testBlockingLatest() {

        Iterable<Long> iterable = Observable.interval(10, TimeUnit.MILLISECONDS)
                .take(100).blockingLatest();

        for (long n : iterable) {
            Sleep.sleep(100);
            System.out.println(n);
        }

    }

    @Test
    public void testBlockingMostRecent() {

        Iterable<Long> iterable = Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(100).blockingMostRecent(-1L);

        for (long n : iterable) {
            Sleep.sleep(10);
            System.out.println(n);
        }

    }
}
