package org.example;

import org.junit.Assert;
import org.junit.Test;

public class DemoJUnit {

    @Test
    public void testHelloJUnit() {
        System.out.println("Esto se ejecuta en un ambiente de pruebas");
    }

    @Test
    public void testSum() {
        int s = 0;
        for (int i = 1; i <= 100; i++) {
            s += i;
        }
        Assert.assertEquals(100 * 101 / 2, s);
        Assert.assertTrue(s == 5050);
        Assert.assertTrue(s == 5052);
        Assert.assertTrue(s == 5053);
    }

}
