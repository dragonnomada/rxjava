package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOperator;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.DisposableObserver;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class SwingOperators {

    public static ObservableOperator<JButton, String> createButtons() {
        return observerButtons -> new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String title) {
                JButton button = new JButton();
                button.setText(title);
                observerButtons.onNext(button);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    public static ObservableOperator<JFrame, String> createWindowFromButtonTitles(ActionListener handler) {

        return observerJFrame -> new DisposableObserver<String>() {
            List<JButton> buttons = new ArrayList<>();

            @Override
            public void onNext(@NonNull String buttonTitle) {
                JButton button = new JButton();
                button.setText(buttonTitle);
                button.addActionListener(handler);
                buttons.add(button);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                // TODO: Generar un JFrame con todos los botones
                JFrame frame = new JFrame();

                JPanel panel = new JPanel();

                for (JButton button : buttons) {
                    panel.add(button);
                }

                frame.add(panel);

                frame.setSize(400, 400);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                observerJFrame.onNext(frame);
                observerJFrame.onComplete();
            }
        };

    }

}

public class DemoLiftOperator {

    public static void main(String[] args) {

        ObservableOperator<JFrame, String> generateJFrames = new ObservableOperator<JFrame, String>() {
            @Override
            public @NonNull Observer<? super String> apply(@NonNull Observer<? super JFrame> observer) throws Throwable {
                return new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        JFrame app = new JFrame();
                        app.setTitle(s);
                        app.setSize(400, 400);
                        observer.onNext(app);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
            }
        };

        /*Observable.just("Ventana 1", "Ventana 2", "Ventana 3", "Hello App")
                .lift(generateJFrames)
                .subscribe(app -> {
                    app.setVisible(true);
                });*/

        Observable.just("Botón 1", "Botón 2", "Pulsame")
                .lift(SwingOperators.createWindowFromButtonTitles(e -> {
                    JButton button = (JButton) e.getSource();
                    System.out.printf("Se ha dado click en: %s %n", button.getText());
                }))
                .subscribe(app -> {
                    app.setVisible(true);
                });

        new Thread().start();

    }

}
