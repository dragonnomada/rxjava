package org.example;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicInteger;

public class DemoFlowableSubscriber {

    public static void main(String[] args) {

        Flowable.just("Manza", "Pera", "Kiwi").repeat(1_000)
                .subscribe(new Subscriber<String>() {
                    AtomicInteger contador = new AtomicInteger(0);
                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(10);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                        if (contador.getAndIncrement() % 10 == 0) {
                            System.out.println("Despachando otros 10 :D");
                            subscription.request(10);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
