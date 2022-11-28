package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoRequestApi {

    public static void main(String[] args) {

        RandomUserRequestObservable.request()
                .subscribe(result -> System.out.printf("[1]: %s %n", result));
        RandomUserRequestObservable.request()
                .subscribe(result -> System.out.printf("[2]: %s %n", result));

        Observable<String> api = RandomUserRequestObservable.requestConnectable()
                .autoConnect(2);

        api.subscribe(result -> System.out.printf("[1*]: %s %n", result));
        api.subscribe(result -> System.out.printf("[2*]: %s %n", result));

    }

}
