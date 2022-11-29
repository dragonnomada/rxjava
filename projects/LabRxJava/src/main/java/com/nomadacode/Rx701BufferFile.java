package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.io.*;
import java.util.Scanner;

public class Rx701BufferFile {

    static void readLines(String fileName, Subject<String> subject) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            subject.onError(e);
            return;
        }

        while (scanner.hasNextLine()) {
            subject.onNext(scanner.nextLine());
        }

        subject.onComplete();

        scanner.close();
    }

    public static void main(String[] args) throws IOException {

        Subject<String> lines = PublishSubject.create();

        lines.map(line -> line.split("\\s"))
                .flatMap(parts ->
                        Observable.fromArray(parts)
                                .map(part -> Integer.parseInt(part))
                )
                .buffer(5)
                .subscribe(System.out::println);

        readLines("D:\\Datasets\\data.txt", lines);

    }

}
