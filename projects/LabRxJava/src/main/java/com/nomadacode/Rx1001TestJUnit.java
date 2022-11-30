package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Rx1001TestJUnit {

    @Test
    public void test1() {
        System.out.println("Prueba 1 realizada");
    }

    @Test
    public void test2() throws Exception {
        throw new Exception("Error en la Prueba 2");
    }

    @Test
    public void testObservableInterval() {
        AtomicInteger count = new AtomicInteger(0);

        Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
                .blockingSubscribe(i -> count.getAndIncrement());

        Assert.assertTrue(String.format("count = %d", count.get()), count.get() == 5);
    }

    @Test
    public void testObservableFail() {
        AtomicInteger count = new AtomicInteger(0);

        Observable.interval(1, TimeUnit.SECONDS)
                .take(6)
                .blockingSubscribe(i -> count.getAndIncrement());

        Assert.assertTrue(String.format("count = %d", count.get()), count.get() == 5);
    }

    @Test
    public void testObservableReduce() {
        AtomicInteger result = new AtomicInteger(0);

        Observable.range(1, 100)
                .reduce((sum, i) -> sum  + i)
                .blockingSubscribe(i -> result.set(i.intValue()));

        Assert.assertEquals(5050, result.get());
    }

    @Test
    public void testObservableReduceFail() {
        AtomicInteger result = new AtomicInteger(0);

        Observable.range(1, 101)
                .reduce((sum, i) -> sum  + i)
                .blockingSubscribe(i -> result.set(i.intValue()));

        Assert.assertEquals(5050, result.get());
    }

}
