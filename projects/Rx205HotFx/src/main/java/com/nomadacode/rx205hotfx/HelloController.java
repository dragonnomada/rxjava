package com.nomadacode.rx205hotfx;

import io.reactivex.rxjava3.core.Observable;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ToggleButton toggleButton;

    Observable<Boolean> observable;

    public HelloController() {
        observable = Observable.create(emitter -> {
            emitter.onNext(toggleButton.selectedProperty().getValue());
            toggleButton.selectedProperty().addListener((observableValue, prev, current) -> emitter.onNext(current));
        });
    }

    @FXML
    public void initialize() {
        observable.map(selected -> selected ? "ACTIVO" : "INACTIVO")
                .subscribe(welcomeText::setText);

        observable.map(selected -> selected ? "DESACTIVAR" : "ACTIVAR")
                .subscribe(toggleButton::setText);
    }
}