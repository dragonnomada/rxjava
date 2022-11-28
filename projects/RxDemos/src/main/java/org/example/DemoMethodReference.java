package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

class UI {

    void menu(int opcion) {

        switch (opcion) {
            case 1:
                saludar();
                break;
            case 2:
                mostrarFecha();
                break;
        }

    }

    void saludar() {
        System.out.println("Hola mundo");
    }

    void mostrarFecha() {
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String date = dateObj.format(formatter);
        System.out.println(date);
    }

}

public class DemoMethodReference {

    public static void main(String[] args) {

        UI ui = new UI();

        Observable<Integer> sourceEventos = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> Math.random() >= 0.5 ? 2 : 1)
                .publish()
                .autoConnect(2);

        sourceEventos.subscribe(opcion -> System.out.printf("Opción: %d %n", opcion));

        // Usamos un método de referencia (tipo objeto)
        // sourceEventos.subscribe(opcion -> ui.menu(opcion))
        sourceEventos.subscribe(ui::menu);

        Sleep.sleep(20000);

    }

}
