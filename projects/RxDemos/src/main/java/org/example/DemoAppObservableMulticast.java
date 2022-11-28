package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DemoAppObservableMulticast extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JLabel label1;

    public DemoAppObservableMulticast() {

        Observable<String> sourceDate = Observable.create(emitter -> {
            button1.addActionListener(e -> {
                LocalDateTime dateObj = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                String date = dateObj.format(formatter);
                emitter.onNext(date);
            });
        });

        ConnectableObservable<String> sourceDateMulticast = sourceDate.publish();

        sourceDateMulticast.subscribe(date -> label1.setText(date));
        sourceDateMulticast.subscribe(date -> System.out.println(date));

        sourceDateMulticast.connect();

        this.add(panel1);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        DemoAppObservableMulticast app = new DemoAppObservableMulticast();

        app.setVisible(true);

    }

}
