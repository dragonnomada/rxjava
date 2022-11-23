package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class DemoObseverManual {

    public static void main(String[] args) {

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Estableciendo conexión...");
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                System.out.printf("Esperando (%d)... %n", aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Falló la conexión");
            }

            @Override
            public void onComplete() {
                System.out.println("La conexión fue establecida correctamente");
            }
        };

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(observer);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
