package org.example;

import io.reactivex.rxjava3.core.Observable;

public class DemoObservableGrouping {

    public static void main(String[] args) {

        Observable.range(1, 100)
                .groupBy(i -> i % 4)
                .flatMapSingle(group -> {
                    System.out.println(group.getKey());
                    return group.toList();
                    //return group.reduce(0, (s, i) -> s + i);
                })
                .subscribe(group -> {
                    System.out.println(group);
                });

    }

}
