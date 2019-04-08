package com.example.recetario.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.recetario.base.AdminSQLiteOpenHelper;
import com.example.recetario.modelo.Ingrediente;

import java.util.ArrayList;
import java.util.List;

public class IngredienteDao {
    final private String BASE = "RECETARIO";
    final private String NUEVO_ID = "SELECT IFNULL(MAX(ID_INGREDIENTE),0)+1 FROM INGREDIENTES";
    final private String ALTA = "INSERT INTO INGREDIENTES VALUES(?,?)";
    final private String VER = "SELECT * FROM INGREDIENTES";
    final private String BAJA = "DELETE FROM INGREDIENTES WHERE ID_INGREDIENTE = ?";

    public void alta(Context context, Ingrediente ingrediente) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase(); // la ponemos en modo de lectura y escritura

        // Conseguimos el id de la receta con la que se registrará
        Cursor fila = baseDeDatos.rawQuery(NUEVO_ID, null);
        fila.moveToFirst();
        int id = fila.getInt(0);
        ingrediente.setIdIngrediente(id); // le ponemos el id del ingrediente por registrar

        SQLiteStatement s = baseDeDatos.compileStatement(ALTA);
        s.clearBindings();
        s.bindLong(1, ingrediente.getIdIngrediente());
        s.bindString(2, ingrediente.getNombre());
        s.executeInsert();
        s.close();
        fila.close();
        baseDeDatos.close();
    }

    public List<Ingrediente> ver(Context context) {
        List<Ingrediente> ver = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
        Cursor fila = baseDeDatos.rawQuery(VER, null);

        while(fila.moveToNext())
            ver.add(new Ingrediente(fila.getInt(0), fila.getString(1)));

        fila.close();
        baseDeDatos.close();
        return ver;
    }

    public void baja(Context context, Ingrediente ingrediente) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

        SQLiteStatement s = baseDeDatos.compileStatement(BAJA);
        s.clearBindings();
        s.bindLong(1, ingrediente.getIdIngrediente());
        s.executeUpdateDelete(); // Borrar

        s.close();
        baseDeDatos.close();
    }
}