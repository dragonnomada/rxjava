package com.nomadacode;

public class HelloLibro implements HelloProducto {

    // Atributos
    String autor;
    String titulo;
    int año;
    double precio;
    int existencias;

    // Constructor -> Inicializo los atributos
    HelloLibro(String autor, String titulo, int año, double precio, int existencias) {
        this.autor = autor;
        this.titulo = titulo;
        this.año = año;
        this.precio = precio;
        this.existencias = existencias;
    }

    @Override
    public String getNombre() {
        return String.format("%s - %s [%d]", autor, titulo, año);
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public int getExistencias() {
        return existencias;
    }

    public void describir() {
        System.out.printf("[%d] %s - %s $%.2f (%d) %n", año, autor, titulo, precio, existencias);
    }

    public static void main(String[] args) {

        HelloLibro libro1 = new HelloLibro("Paco Díaz", "Un libro más en el mundo", 2022, 9.99, 1000);

        libro1.describir();

        HelloLibro libro2 = new HelloLibro("Pepe Sánchez", "El último libro en el universo", 2003, 4.67, 345);

        libro2.describir();

    }

}
