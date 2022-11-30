package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Rx1004TestScheduler {

    @Test
    public void testTestScheduler() {

        TestScheduler testScheduler = new TestScheduler();

        TestObserver<Long> testObserver = new TestObserver<>();

        Observable<Long> source = Observable.interval(1, TimeUnit.MINUTES, testScheduler);

        source.subscribe(testObserver);

        // 60s (1 min)
        testScheduler.advanceTimeBy(60, TimeUnit.SECONDS);
        testObserver.assertValue(0L);

        // 90s (+1 min)
        testScheduler.advanceTimeBy(30, TimeUnit.SECONDS);
        testObserver.assertValue(0L);

        // 130s (+2 min)
        testScheduler.advanceTimeBy(40, TimeUnit.SECONDS);
        testObserver.assertValues(0L, 1L);

        // 340s (+5 min)
        testScheduler.advanceTimeBy(210, TimeUnit.SECONDS);
        testObserver.assertValues(0L, 1L, 2L, 3L, 4L);

    }

}
