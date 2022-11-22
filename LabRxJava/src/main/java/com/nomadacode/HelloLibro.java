package com.nomadacode;

public class Libro {

    // Atributos
    String autor;
    String titulo;
    int año;
    double precio;
    int existencias;

    // Constructor -> Inicializo los atributos
    Libro(String autor, String titulo, int año, double precio, int existencias) {
        this.autor = autor;
        this.titulo = titulo;
        this.año = año;
        this.precio = precio;
        this.existencias = existencias;
    }

    void describir() {
        System.out.printf("[%d] %s - %s $%.2f (%d) %n", año, autor, titulo, precio, existencias);
    }

    public static void main(String[] args) {

        Libro libro1 = new Libro("Paco Díaz", "Un libro más en el mundo", 2022, 9.99, 1000);

        libro1.describir();

        Libro libro2 = new Libro("Pepe Sánchez", "El último libro en el universo", 2003, 4.67, 345);

        libro2.describir();

    }

}
