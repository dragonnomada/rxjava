package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoObservableGrouping {

    public static void main(String[] args) {

        Observable.range(1, 100)
                .groupBy(i -> i % 2)
                .subscribe(group -> {
                    System.out.println(group);
                });

    }

}
