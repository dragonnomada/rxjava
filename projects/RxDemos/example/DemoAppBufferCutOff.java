package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DemoAppBufferCutOff extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JTextArea textArea1;
    private JLabel label1;

    public DemoAppBufferCutOff() {

        Observable<LocalDateTime> actionButtonObservable = Observable.create(emitter -> {
            button1.addActionListener(e -> emitter.onNext(LocalDateTime.now()));
        });

        AtomicInteger ultimoConteo = new AtomicInteger(0);

        Observable.interval(200, TimeUnit.MILLISECONDS)
                .doOnNext(i -> label1.setText(String.format("New tweets (%d)", i - ultimoConteo.get() + 1)))
                .throttleLast(2, TimeUnit.SECONDS) // Durante 2 segundos la última emisión
                //.throttleFirst(2, TimeUnit.SECONDS) // Durante 2 segundos la primer emisión
                //.throttleWithTimeout(2, TimeUnit.SECONDS) // Durante 2 segundos sin emisión lanza la última
                .buffer(actionButtonObservable)
                .flatMap(batch ->
                        Observable.fromIterable(batch)
                                .map(i -> {
                                    ultimoConteo.set(i.intValue());
                                    return String.format("Tweet: %d", i);
                                })
                                .startWithItem("-------")
                )
                .subscribe(item -> textArea1.setText(textArea1.getText() + "\n" + item));

        // Simulamos un feed que genera números cada 200 milisegundos
        /*Observable<Observable<Long>> feed = Observable.interval(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                // El observable de intervalo va a crear un lote
                // cada que el observable del botón emita una señal
                // esto es considerado un cut-off
                .doOnNext(i -> {
                    label1.setText(String.format("New tweets (%d)", i - ultimoConteo.get() + 1));
                })
                //.buffer(5, TimeUnit.SECONDS)
                //.buffer(actionButtonObservable)
                //.flatMap(batch ->
                //        Observable.fromIterable(batch)
                //                .map(i -> {
                //                    ultimoConteo.set(i.intValue());
                //                    return String.format("Tweet: %d", i);
                //                })
                //                .startWithItem("-------")
                //)
                .window(actionButtonObservable)
                .publish()
                .autoConnect(1);

        feed.concatMapSingle(observable -> observable
                        .map(i -> String.format("Tweet: %d", i))
                        .startWithItem("-------")
                        .reduce("", (text, item) -> text + "\n" + item))
                .subscribe(item -> {
                    textArea1.setText(textArea1.getText() + "\n" + item
                    );
                });

        feed.concatMapSingle(obs -> obs.toList().toObservable().count()).subscribe(totalSingle -> {
            System.out.println(totalSingle);
            //totalSingle.subscribe(total -> ultimoConteo.set(total.intValue()));
        });*/

        this.add(panel1);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        DemoAppBufferCutOff app = new DemoAppBufferCutOff();

        app.setVisible(true);

    }

}
