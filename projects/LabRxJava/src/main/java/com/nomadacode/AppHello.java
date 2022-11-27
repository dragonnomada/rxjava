package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

public class AppHello extends JFrame {

    private JPanel jPanel;
    private JButton button1;
    private JLabel label1;

    private Observable<ActionEvent> buttonObservable;

    public AppHello() {
        this.add(jPanel);

        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        buttonObservable = Observable.create(emitter -> {
            button1.addActionListener(e -> emitter.onNext(e));
        });

        buttonObservable.map(e -> String.format("Hello world (%.2f)", Math.random()))
                .doOnNext(message -> label1.setText(message))
                .doOnNext(s -> button1.setEnabled(false))
                .delay(3, TimeUnit.SECONDS)
                .doOnNext(s -> button1.setEnabled(true))
                .subscribe();
    }

    public static void main(String[] args) {

        AppHello app = new AppHello();

    }

}
