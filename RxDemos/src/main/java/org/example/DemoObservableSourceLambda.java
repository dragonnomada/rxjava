package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;

public class DemoObservableSourceLambda {

    public static void main(String[] args) {

        Observable<Integer> observablePrimos = Observable.create(emitter -> {

            ArrayList<Integer> primos = new ArrayList<>();
            primos.add(2);
            emitter.onNext(2);
            try {
                Thread.sleep(100);
            } catch (Throwable e) {
                emitter.onError(e);
            }
            int n = 3;
            while (true) {
                boolean esPrimo = true;
                for (int p : primos) {
                    if (n % p == 0) {
                        esPrimo = false;
                        break;
                    }
                }
                if (esPrimo) {
                    primos.add(n);
                    emitter.onNext(n);
                }
                n++;
                try {
                    Thread.sleep(100);
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
            //emitter.onComplete();

        });

        observablePrimos.subscribe(primo -> System.out.printf("Primo: %d %n", primo));

    }

}
