package org.example.entinty;

import java.sql.Timestamp;

public class Prueba {

    private int id;
    private String nombre;
    private Timestamp creado;
    private Timestamp actualizado;
    private boolean activo;

    public Prueba(int id, String nombre, Timestamp creado, Timestamp actualizado, boolean activo) {
        this.id = id;
        this.nombre = nombre;
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
        return "Prueba{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", creado=" + creado +
                ", actualizado=" + actualizado +
                ", activo=" + activo +
                '}';
    }
}
