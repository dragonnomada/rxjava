package com.nomadacode;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Rx804BackpressureBuffer {

    public static void main(String[] args) {

        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer(10_000,
                        () -> {
                            // TODO: UI buffer superado
                            System.out.println("Buffer desbordado");
                        },
                        //() -> System.out.println("Buffer superado"),
                        //BackpressureOverflowStrategy.DROP_LATEST
                        //BackpressureOverflowStrategy.DROP_OLDEST
                        BackpressureOverflowStrategy.ERROR
                )
                .onErrorReturnItem(-1L)
                .observeOn(Schedulers.io())
                .subscribe(i -> {
                    Sleep.sleep(50);
                    System.out.printf("RECIBIDO: %d %n", i);
                });

        Sleep.sleep(30000);

    }

}
