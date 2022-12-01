package org.example;

import java.io.Serializable;
import java.sql.Timestamp;

public class Producto implements Serializable {

    private int id;
    private String nombre;
    private double precio;
    private int existencias;
    private Timestamp creado;
    private Timestamp actualizado;
    private boolean activo;

    public Producto(int id, String nombre, double precio, int existencias, Timestamp creado, Timestamp actualizado, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.existencias = existencias;
        this.creado = creado;
        this.actualizado = actualizado;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getExistencias() {
        return existencias;
    }

    public Timestamp getCreado() {
        return creado;
    }

    public Timestamp getActualizado() {
        return actualizado;
    }

    public boolean isActivo() {
        return activo;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", existencias=" + existencias +
                ", creado=" + creado +
                ", actualizado=" + actualizado +
                ", activo=" + activo +
                '}';
    }
}
