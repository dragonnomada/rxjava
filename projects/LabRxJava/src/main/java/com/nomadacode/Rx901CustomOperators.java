package com.nomadacode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;

public class Rx901CustomOperators {

    public static ObservableTransformer<String, Integer> maxLength() {
        return new ObservableTransformer<String, Integer>() {
            @Override
            public @NonNull ObservableSource<Integer> apply(@NonNull Observable<String> upstream) {
                return upstream.map(String::length)
                        .sorted()
                        .lastElement()
                        .toObservable();
            }
        };
    }

    public static ObservableTransformer<String, String> join(String separator) {
        return new ObservableTransformer<String, String>() {
            @Override
            public @NonNull ObservableSource<String> apply(@NonNull Observable<String> upstream) {
                return upstream.reduce("",
                                (text, element) ->
                                        text == "" ?
                                                element :
                                                text + separator + element
                        )
                        .toObservable();
            }
        };
    }

}
