package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class Rx901ComposeCustomOperator {

    public static void main(String[] args) {

        Observable.just("Fresa", "Mango", "Guanabana", "Melocotón", "Sandía")
                .compose(Rx901CustomOperators.maxLength())
                .subscribe(System.out::println);

        Observable.just("Fresa", "Mango", "Guanabana", "Melocotón", "Sandía")
                .compose(Rx901CustomOperators.join("::"))
                .subscribe(System.out::println);

    }

}
