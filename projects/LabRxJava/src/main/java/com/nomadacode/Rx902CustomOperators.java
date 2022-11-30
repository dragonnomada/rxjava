package com.nomadacode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOperator;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.observers.DisposableObserver;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Rx902CustomOperators {

    public static ObservableOperator<Set<Character>, String> charset() {
        return new ObservableOperator<Set<Character>, String>() {
            @Override
            public @NonNull Observer<? super String> apply(@NonNull Observer<? super Set<Character>> observer) throws Throwable {
                return new DisposableObserver<String>() {
                    Set<Character> characters = new HashSet<>();

                    @Override
                    public void onNext(@NonNull String s) {
                        s = Normalizer.normalize(s, Normalizer.Form.NFD);
                        s = s.replaceAll("[^\\p{ASCII}]", "");
                        for (Character c : s.toLowerCase().toCharArray()) {
                            characters.add(c);
                        }
                     }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onNext(characters);
                        observer.onComplete();
                    }
                };
            }
        };
    }

}
