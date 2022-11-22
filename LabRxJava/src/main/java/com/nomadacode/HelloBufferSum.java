package com.nomadacode;

// Objeto -> Estado interno (Memoria/Contexto)
public class HelloBufferSum {

    // Atributo -> Propiedad | Memoria | Estado
    int[] valores = new int[10];

    // Tarea 1: ajustar(int, int) -> void
    void ajustar(int i, int x) {
        valores[i] = x;
    }

    // Tarea 2: ajustar(int[]) -> void
    void ajustar(int[] valores) throws Exception {
        if (this.valores.length != valores.length) {
            throw new Exception("Los valores no coinciden en tamaño");
        }
        this.valores = valores;
    }

    // Método -> Tarea | Acción | Función
    int sumar() {
        int s = 0;
        for (int i = 0; i < valores.length; i++) {
            s += valores[i]; // s = s + valores[i]
        }
        return s;
    }

    public static void main(String[] args) {

        HelloBufferSum bufferSum = new HelloBufferSum();

        System.out.println(bufferSum.sumar());

        bufferSum.ajustar(1, 17);
        bufferSum.ajustar(5, 28);

        System.out.println(bufferSum.sumar());

    }

}
