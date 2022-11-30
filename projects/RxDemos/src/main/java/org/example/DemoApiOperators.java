package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class ApiOperators {

    public static ObservableTransformer<String, Integer> downloadBytes(long limit) {
        return uris -> uris.compose(downloadBytesFull()).take(limit);
    }

    public static ObservableTransformer<String, Integer> downloadBytesFull() {
        return uris -> uris
                .map(textUrl -> new URL(textUrl))
                .map(url -> url.openConnection())
                .map(conn -> conn.getInputStream())
                .flatMap(inputStream -> {
                    List<Integer> bytes = new ArrayList<>();
                    int b = 0;
                    while ((b = inputStream.read()) != -1) {
                        bytes.add(b);
                    }
                    inputStream.close();
                    return Observable.fromIterable(bytes);
                });
    }

}

public class DemoApiOperators {

    public static void main(String[] args) {

        AtomicReference<URLConnection> urlConnection = new AtomicReference<>();

        Observable<String> urls = Observable.just("https://...", "https://...", "https://...")
                // DOWNLOAD FILE
                .doFinally(() -> {
                    //urlConnection.get().close();
                })
                .map(textUrl -> new URL(textUrl))
                .map(url -> url.openConnection())
                .map(conn -> {
                    urlConnection.set(conn);
                    return conn.getInputStream();
                })
                .flatMap(inputStream -> {
                    List<Integer> bytes = new ArrayList<>();
                    int b = 0;
                    while ((b = inputStream.read()) != -1) {
                        bytes.add(b);
                    }
                    inputStream.close();
                    return Observable.fromIterable(bytes);
                })
                .map(b -> b.toString());

        Observable.just("https://...", "https://...", "https://...")
                .compose(ApiOperators.downloadBytes(1_000_000))
                .subscribe(b -> System.out.println(b));

    }

}
