package com.nomadacode;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicInteger;

public class Rx803FlowableSubscriber {

    public static void main(String[] args) {

        Flowable.range(1, 1_000)
                .doOnNext(i -> System.out.printf("RECIBIDO: %d %n", i))
                .observeOn(Schedulers.io())
                .map(i -> {
                    Sleep.sleep(50);
                    return i;
                })
                .subscribe(new Subscriber<Integer>() {
                    Subscription subscription;
                    AtomicInteger countRequests = new AtomicInteger(1);

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(40);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Sleep.sleep(50);
                        System.out.printf("RECIBIDO: %d (%d) %n", integer, countRequests.get());
                        if (countRequests.getAndIncrement() % 20 == 0 && countRequests.get() >= 40) {
                            System.out.printf("Solicitando 20 emisiones más (%d) %n", countRequests.get());
                            subscription.request(20);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("FLUJO FINALIZADO");
                    }
                });

        Sleep.sleep(20000);

    }

}
