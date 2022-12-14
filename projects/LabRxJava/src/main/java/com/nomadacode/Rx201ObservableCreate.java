package com.nomadacode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class Rx201ObservableCreate {

    public static void main(String[] args) {

        ObservableOnSubscribe<String> source1 = new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("Uno");
                emitter.onNext("Dos");
                emitter.onNext("Tres");
                emitter.onComplete();
            }
        };

        Observable<String> observable1 = Observable.create(source1);

        observable1.subscribe(value -> System.out.println(value));

        Observable<String> observable2 = Observable.create(emitter -> {
            emitter.onNext("Alpha");
            emitter.onNext("Beta");
            emitter.onNext("Gamma");
            emitter.onComplete();
        });

        observable2.subscribe(value -> System.out.println(value));

        Observable<Integer> observable3 = Observable.create(emitter -> {
            try {
                for (int i = 0; i <= 10; i++) {
                    emitter.onNext(100 / (10 - i));
                }
                emitter.onComplete();
            } catch (Throwable e) {
                emitter.onError(e);
            }
        });

        observable3.subscribe(
                value -> System.out.println(value),
                error -> System.out.printf("ERROR: %s %n", error.getMessage())
        );

    }

}
