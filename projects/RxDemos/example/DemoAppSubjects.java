package org.example;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DemoAppSubjects extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JLabel label1;

    private Subject<String> mostrarFechaUsuario = PublishSubject.create();

    public DemoAppSubjects() {
        mostrarFechaUsuario.map(date -> String.format("[%s]", date))
                .subscribe(date -> label1.setText(date));
        //mostrarFechaUsuario.subscribe(label1::setText);
        mostrarFechaUsuario.subscribe(date -> System.out.println(date));
        //mostrarFechaUsuario.subscribe(System.out::println);

        button1.addActionListener(e -> {
            LocalDateTime dateObj = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            String date = dateObj.format(formatter);
            mostrarFechaUsuario.onNext(date);
        });

        this.add(panel1);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        DemoAppSubjects app = new DemoAppSubjects();

        app.setVisible(true);
    }
}
