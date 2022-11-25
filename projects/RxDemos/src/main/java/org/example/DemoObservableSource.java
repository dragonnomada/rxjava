package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;

import java.util.ArrayList;

public class DemoObservableSource {

    public static void main(String[] args) {

        ObservableOnSubscribe<Integer> sourcePrimos = new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                ArrayList<Integer> primos = new ArrayList<>();
                primos.add(2);
                emitter.onNext(2);
                int n = 3;
                while (primos.size() < 20) {
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
                }
                emitter.onComplete();
            }
        };

        Observable<Integer> observablePrimos = Observable.create(sourcePrimos);

        observablePrimos.subscribe(primo -> System.out.println(primo));

        observablePrimos.subscribe(primo -> System.out.printf("Primo: %d %n", primo));

        Observable<Integer> observablePrimosD9 = observablePrimos.filter(primo -> primo % 10 == 9);

        observablePrimosD9.subscribe(primoD9 -> System.out.printf("Primo D9: %d %n", primoD9));

        Maybe<Integer> observablePrimosMenores50Suma = observablePrimos
                .filter(primos -> primos < 50)
                .reduce((suma, primo) -> suma + primo);

        observablePrimosMenores50Suma.subscribe(suma -> System.out.printf("Suma primos menores 50: %d %n", suma));

        observablePrimos.take(5).subscribe(primo -> System.out.println(primo));

    }

}
