package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.concurrent.TimeUnit;

public class DemoObservableConsumer {

    public static void main(String[] args) {

        Observable.just("Manzana", "Pera", "Kiwi")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        System.out.printf("RECIBIDO: %s %n", s);
                    }
                });

    }

}
