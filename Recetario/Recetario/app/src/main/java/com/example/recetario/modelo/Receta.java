package com.example.recetario.modelo;

import android.graphics.Bitmap;

public class Receta {
    private int idReceta;
    private int idUsuario;
    private String titulo;
    private String procedimiento;
    private int calificacion;
    private Bitmap imagen;

    public Receta(int idReceta, int idUsuario, String titulo, String procedimiento, int calificacion, Bitmap imagen) {
        this.idReceta = idReceta;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.procedimiento = procedimiento;
        this.calificacion = calificacion;
        this.imagen = imagen;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "idReceta=" + idReceta +
                ", idUsuario=" + idUsuario +
                ", titulo='" + titulo + '\'' +
                ", procedimiento='" + procedimiento + '\'' +
                ", calificacion='" + calificacion + '\'' +
                ", imagen=" + imagen +
                '}';
    }
}
