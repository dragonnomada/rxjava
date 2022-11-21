module com.nomadacode.rx205hotfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires io.reactivex.rxjava3;

    opens com.nomadacode.rx205hotfx to javafx.fxml;
    exports com.nomadacode.rx205hotfx;
}