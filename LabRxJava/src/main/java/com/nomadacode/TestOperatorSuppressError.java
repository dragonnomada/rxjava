package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

public class TestOperatorSupressError {

    public static void main(String[] args) {

         Observable.just(1, 2, 3)
                 .flatMap(i -> {
                     return Observable.just(i).map(j -> {
                         if (j == 2) throw new Exception("Error 2");
                         return Math.pow(j, 2);
                     }).onErrorResumeWith(Observable.empty());
                     // .onErrorReturn(throwable -> 0.0);
                     // .onErrorReturnItem(0.0);
                     // .onErrorResumeNext(throwable -> Observable.empty());
                     // .onErrorResumeWith(Observable.empty());
                 })
                 .subscribe(System.out::println);


    }

}
