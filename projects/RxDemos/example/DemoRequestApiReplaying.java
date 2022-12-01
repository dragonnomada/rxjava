package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class DemoRequestApiReplaying {

    public static void main(String[] args) {

        Observable<String> sourceApi = Observable.interval(2, TimeUnit.SECONDS)
                .flatMap(i -> RandomUserRequestObservable.request());

        //ConnectableObservable<String> sourceApiMulticasting = sourceApi.replay();
        //ConnectableObservable<String> sourceApiMulticasting = sourceApi.replay(3);
        //ConnectableObservable<String> sourceApiMulticasting = sourceApi.replay(6, TimeUnit.SECONDS);
        ConnectableObservable<String> sourceApiMulticasting = sourceApi.replay(1, 6, TimeUnit.SECONDS);

        sourceApiMulticasting.subscribe(result -> System.out.printf("[1]: %s %n", result));

        sourceApiMulticasting.connect();

        Sleep.sleep(10000);

        sourceApiMulticasting.subscribe(result -> System.out.printf("[2]: %s %n", result));

        Sleep.sleep(6000);
    }

}
