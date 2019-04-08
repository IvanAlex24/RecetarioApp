package com.example.recetario.modelo;

public class IngredienteYReceta {
    private int idIngredienteYReceta;
    private int idIngrediente;
    private int idReceta;

    public IngredienteYReceta(int idIngredienteYReceta, int idIngrediente, int idReceta) {
        this.idIngredienteYReceta = idIngredienteYReceta;
        this.idIngrediente = idIngrediente;
        this.idReceta = idReceta;
    }

    public int getIdIngredienteYReceta() {
        return idIngredienteYReceta;
    }

    public void setIdIngredienteYReceta(int idIngredienteYReceta) {
        this.idIngredienteYReceta = idIngredienteYReceta;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    @Override
    public String toString() {
        return "IngredienteYReceta{" +
                "idIngredienteYReceta=" + idIngredienteYReceta +
                ", idIngrediente=" + idIngrediente +
                ", idReceta=" + idReceta +
                '}';
    }
}
