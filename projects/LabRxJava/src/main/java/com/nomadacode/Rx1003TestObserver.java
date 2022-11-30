package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Rx1003TestObserver {

    @Test
    public void testTestObserver() throws InterruptedException {
        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS)
                .take(3);

        TestObserver<Long> testObserver = new TestObserver<>();

        Assert.assertFalse(testObserver.hasSubscription());

        source.subscribe(testObserver);

        Assert.assertTrue(testObserver.hasSubscription());

        testObserver.await(4, TimeUnit.SECONDS);

        testObserver.assertComplete();

        testObserver.assertNoErrors();

        testObserver.assertValueCount(3);

        testObserver.assertValues(0L, 1L, 2L);
    }

}
