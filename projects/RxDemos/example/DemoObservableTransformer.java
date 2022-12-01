package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;

class UtilOperators {

    public static ObservableTransformer<Integer, String> toEvenOddLabel(String evenLabel, String oddLabel) {
        return sourceInteger -> sourceInteger
                .map(i -> i % 2 == 0 ? evenLabel : oddLabel);
    }

}

public class DemoObservableTransformer {

    public static void main(String[] args) {

        ObservableTransformer<Integer, String> operator = sourceInteger -> sourceInteger
                .map(i -> i % 2 == 0 ? "PAR" : "IMPAR");

        Observable<Integer> numeros = Observable.range(1, 4);

        numeros.compose(operator)
                .subscribe(resultado -> System.out.println(resultado));

        numeros.compose(UtilOperators.toEvenOddLabel("A", "B"))
                .subscribe(resultado -> System.out.println(resultado));

        numeros.map(i -> i % 2 == 0 ? "X" : "Y")
                .subscribe(resultado -> System.out.println(resultado));

    }

}
