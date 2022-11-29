package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class DemoAppSwitching extends JFrame {
    private JComboBox comboBox1;
    private JTextArea textArea1;
    private JPanel panel1;

    public DemoAppSwitching() {

        comboBox1.addItem("México");
        comboBox1.addItem("Canadá");
        comboBox1.addItem("Estados Unidos");

        Subject<String> countrySelected = PublishSubject.create();

        countrySelected.switchMap(country -> {
            // TODO: Abrir una nueva conexión a la base de datos
            Observable<String> feedCountry = Observable.interval(1, TimeUnit.SECONDS)
                    .doOnDispose(() -> {
                        // TODO: Cerrar la conexión a base de datos
                        textArea1.setText("");
                        System.out.printf("El observador de noticias para [%s] ha sido liberado :D %n", country);
                    })
                    .map(i -> String.format("[%s] %d Texto de la noticia...", country, i));

            return feedCountry;
        }).subscribe(newsByCountry -> {
            textArea1.setText(textArea1.getText() + "\n" + newsByCountry);
        });

        countrySelected.onNext("México");

        comboBox1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println(e.getItem());
                countrySelected.onNext((String) e.getItem());
            }
        });

        new Thread(() -> {
            Sleep.sleep(10000);
            countrySelected.onNext("Uganda");
        }).start();

        this.add(panel1);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        DemoAppSwitching app = new DemoAppSwitching();

        app.setVisible(true);

    }
}
