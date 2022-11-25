package org.example;

import java.util.ArrayList;

public class DemoGeneradorPrimos {

    public static void main(String[] args) {

        ArrayList<Integer> primos = new ArrayList<>();
        primos.add(2);
        System.out.println(2);
        int n = 3;
        while (primos.size() < 20) {
            boolean esPrimo = true;
            for (int p : primos) {
                if (n % p == 0) {
                    esPrimo = false;
                    break;
                }
            }
            if (esPrimo) {
                primos.add(n);
                System.out.println(n);
            }
            n++;
        }

    }

}
