package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Rx306CollectionOperators {

    public static void main(String[] args) {

        System.out.println("toList()");

        Observable.range(1, 10)
                .toList()
                .subscribe(list -> System.out.println(list));

        System.out.println("toSortedList()");

        Observable.just(5, 3, 2, 6, 4, 1)
                .toSortedList()
                .subscribe(list -> System.out.println(list));

        System.out.println("toMap");

        Observable.range(1, 20)
                .toMap(i -> i % 5)
                .subscribe(list -> System.out.println(list));

        System.out.println("toMultimap");

        Observable.range(1, 20)
                .toMultimap(i -> i % 5)
                .subscribe(list -> System.out.println(list));

        System.out.println("collect");

        Observable.range(1, 20)
                .collect(HashMap<Integer, String>::new, (integers, integer) -> {
                    integers.put(integer, String.format("[%d]", integer));
                })
                .subscribe(list -> System.out.println(list));

    }

}
