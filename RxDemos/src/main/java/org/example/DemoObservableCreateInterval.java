package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoObservableCreateInterval {

    public static void main(String[] args) {

        Observable<Long> sourceInterval = Observable.create(emitter -> {

            Long contador = 0L;

            while (true) {
                try {
                    emitter.onNext(contador++);
                    Thread.sleep(1000);
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }

        });

        sourceInterval.subscribe(System.out::println);

        try {
            Thread.sleep(10000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
