package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

interface IPagable {
    boolean pagar();
}

interface IOnPago {
    void pago(boolean state);
}

class PagoConTarjeta implements IPagable {

    private List<IOnPago> listeners = new ArrayList();

    public boolean pagar() {
        boolean pagado = true;
        if (Math.random() < 0.5) {
            pagado = false;
        }
        for (IOnPago listener : listeners) {
            listener.pago(pagado);
        }
        return pagado;
    }

    void addListener(IOnPago onPago) {
        listeners.add(onPago);
    }

}

public class DemoObservableEvent {

    public static void main(String[] args) {

        PagoConTarjeta pagoConTarjeta = new PagoConTarjeta();

        Observable<Boolean> sourcePagable = Observable.create(emitter -> {
            pagoConTarjeta.addListener(pagado -> emitter.onNext(pagado));
            // socket.onConnection(node -> emitter.onNext(node.addr()))
            // trigger.onProducere(procedure -> emitter.onNext(procedure))
        });

        sourcePagable.subscribe(pago -> System.out.println(pago ? "PAGADO" : "FALLÓ PAGO"));

        Observable<Long> sourcePagador = Observable.interval(1, TimeUnit.SECONDS);

        sourcePagador.map(i -> pagoConTarjeta).subscribe(pagoConTarjeta1 -> {
            pagoConTarjeta1.pagar();
        });

        try {
            Thread.sleep(10000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
