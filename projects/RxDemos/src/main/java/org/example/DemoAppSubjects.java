package org.example;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoAppSubjects extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JLabel label1;

    private Subject<String> mostrarFechaUsuario = PublishSubject.create();

    public DemoAppSubjects() {
        button1.addActionListener(e -> {
            
        });

        this.add(panel1);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        DemoAppSubjects app = new DemoAppSubjects();

        app.setVisible(true);
    }
}
