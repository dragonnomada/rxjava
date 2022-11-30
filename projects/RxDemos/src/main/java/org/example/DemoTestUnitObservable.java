package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoTestUnitObservable {

    @Test
    public void testObservableReduce() {
        AtomicInteger result = new AtomicInteger();

        Observable.just(17, 13, 18, 25, 32, 15, 19, 21, 23, 45)
                .reduce(Integer.MAX_VALUE, (min, i) -> (i >= 15 && i <= 24 & min > i) ? i : min)
                .blockingSubscribe(min -> result.set(min.intValue()));

        Assert.assertTrue(String.format("Se espera que el menor entre [16, 24] sea mayor a 16. ¿%d >= 16?", result.get()), result.get() >= 15);
        Assert.assertTrue(result.get() <= 24);

        AtomicInteger result2 = new AtomicInteger();

        Observable.just(17, 13, 18, 25, 32, 16, 19, 21, 23, 45)
                .filter(i -> i >= 15)
                .filter(i -> i <= 24)
                .sorted()
                .firstElement()
                .blockingSubscribe(min -> result2.set(min));

        System.out.println("Se espera tal...");
        Assert.assertEquals(result2.get(), result.get());
    }

    @Test
    public void testObservableReduce2() {
        AtomicInteger result1 = new AtomicInteger();
        AtomicInteger result2 = new AtomicInteger();

        Observable<Integer> source = Observable.range(1, 10_000)
                .map(i -> (int) (Math.random() * 100)).publish().autoConnect(2);

        source.reduce(Integer.MAX_VALUE, (min, i) -> (i >= 15 && i <= 24 & min > i) ? i : min)
                .subscribe(min -> result1.set(min));

        source.filter(i -> i >= 15)
                .filter(i -> i <= 24)
                .sorted()
                .firstElement()
                .subscribe(min -> result2.set(min));

        Assert.assertEquals(result2.get(), result1.get());
    }

    @Test
    public void testObservableReduce3() {
        Observable<Integer> source = Observable.range(1, 10_000)
                .map(i -> (int) (Math.random() * 100));

        int result1 = source
                .reduce(Integer.MAX_VALUE, (min, i) -> (i >= 15 && i <= 24 & min > i) ? i : min)
                .blockingGet();

        int result2 = source
                .filter(i -> i >= 15)
                .filter(i -> i <= 24)
                .sorted()
                .firstElement()
                .blockingGet();

        Assert.assertEquals(result2, result1);
    }

    @Test
    public void testObservableReduce4() {
        // Usamos el caché para que las observaciones queden congeladas
        Observable<Integer> source = Observable.range(1, 10_000)
                .map(i -> (int) (Math.random() * 100)).cache();

        Iterable<Integer> iterable1 = source.blockingIterable();

        Iterator<Integer> iterator = iterable1.iterator();

        int s1 = 0;

        int x = 0;

        while ((x = iterator.next()) != 99) {
            s1 += x;
        }

        System.out.printf("Suma esperada: %d %n", s1);

        Iterable<Integer> iterable2 = source.blockingIterable();

        int s2 = Observable.fromIterable(iterable2)
                .takeWhile(i -> i != 99)
                .reduce((s, i) -> s + i)
                .blockingGet();

        System.out.printf("Suma obtenida: %d %n", s2);

        Assert.assertEquals(s1, s2);
    }

    @Test
    public void testObserver() throws InterruptedException {

        Observable<String> sourceNombresProductos = Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
                .map(i -> String.format("Producto %d", i + 1));

        TestObserver<String> testObserver = new TestObserver<>();

        // Acierta que aún no hay suscripción
        Assert.assertFalse(testObserver.hasSubscription());

        // Suscribimos al observador de prueba
        sourceNombresProductos.subscribe(testObserver);

        // Acierta que ya está suscrito
        Assert.assertTrue(testObserver.hasSubscription());

        testObserver.assertNotComplete();

        testObserver.await(6, TimeUnit.SECONDS);

        testObserver.assertComplete();

        testObserver.assertValueCount(5);

        testObserver.assertValues("Producto 1", "Producto 2", "Producto 3", "Producto 4", "Producto 5");

    }

    @Test
    public void testApi() {

        Iterable<String[]> results = Observable.just(23, 45, 67, 89)
                .doOnNext(seed -> System.out.printf("Seed: %d %n", seed))
                .concatMap(seed -> RandomUserRequestObservable.request(seed))
                .doOnNext(textJSON -> System.out.printf("JSON: %s %n", textJSON))
                .map(result -> result.split(","))
                .doOnNext(parts -> System.out.printf("PARTS: %s %n", Arrays.toString(parts)))
                .blockingIterable();

        for (String[] parts : results) {
            System.out.println(Arrays.toString(parts));
        }

    }

}
