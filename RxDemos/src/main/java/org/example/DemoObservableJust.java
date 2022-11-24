package org.example;

import io.reactivex.rxjava3.core.Observable;

class PayState {

    private boolean payed;

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}

public class DemoObservableJust {

    public static void main(String[] args) {

        PayState payState = new PayState();

        Thread t1 = new Thread(() -> {
            int count = 0;
            while (!payState.isPayed()) {
                System.out.printf("Esperando pago (%d)... %n", count++);
                try {
                    Thread.sleep(1000);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Pago exitoso");
        });

        Observable<Thread> source1 = Observable.just(t1);

        Observable<PayState> source2 = Observable.just(payState);

        source1.map(thread -> thread.isAlive())
                .subscribe(isAlive -> System.out.printf("Hilo vivo? %B %n", isAlive));

        t1.start();

        source1.map(thread -> thread.isAlive())
                .subscribe(isAlive -> System.out.printf("Hilo vivo? %B %n", isAlive));

        try {
            Thread.sleep(4000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        source2.subscribe(payState1 -> payState1.setPayed(true));

        try {
            Thread.sleep(100);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        source1.map(thread -> thread.isAlive())
                .subscribe(isAlive -> System.out.printf("Hilo vivo? %B %n", isAlive));

    }

}
