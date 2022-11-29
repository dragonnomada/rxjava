package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class DemoThreading {

    static void taskSecuencial() {
        int a = (int)(Math.random() * 100);
        Sleep.sleep(20000);

        int b = (int)(Math.sqrt(800));
        Sleep.sleep(20000);

        int c = a + b;
        Sleep.sleep(3000);

        System.out.printf("alfa=%d + beta=%d := gamma=%d %n", a, b, c);
    }

    static void taskThreading() {
        AtomicInteger a = new AtomicInteger();
        AtomicInteger b = new AtomicInteger();
        AtomicInteger c = new AtomicInteger();

        Thread t1 = new Thread(() -> {
            a.set((int) (Math.random() * 100));
            Sleep.sleep(20000);
        });

        Thread t2 = new Thread(() -> {
            b.set((int) (Math.sqrt(800)));
            Sleep.sleep(20000);
        });

        Thread t3 = new Thread(() -> {
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            c.set(a.intValue() + b.intValue());
            Sleep.sleep(3000);
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t3.join();
            System.out.printf("alfa=%d + beta=%d := gamma=%d %n", a.intValue(), b.intValue(), c.intValue());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        Util.logDate("[INICIO] ");

        //taskSecuencial(); // 43s
        taskThreading(); // 23s

        Util.logDate("[FIN] ");

    }

}
