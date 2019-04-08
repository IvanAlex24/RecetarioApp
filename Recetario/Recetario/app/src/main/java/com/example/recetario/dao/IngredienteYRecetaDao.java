package com.example.recetario.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.recetario.base.AdminSQLiteOpenHelper;
import com.example.recetario.modelo.IngredienteYReceta;
import com.example.recetario.modelo.Receta;

import java.util.ArrayList;
import java.util.List;

public class IngredienteYRecetaDao {
    final private String BASE = "RECETARIO";
    final private String NUEVO_ID = "SELECT IFNULL(MAX(ID_INGREDIENTE_Y_RECETA),0)+1 FROM INGREDIENTES_Y_RECETAS";
    final private String ALTA = "INSERT INTO INGREDIENTES_Y_RECETAS VALUES(?,?,?)";
    final private String VER = "SELECT * FROM INGREDIENTES_Y_RECETAS";
    final private String BAJA = "DELETE FROM INGREDIENTES_Y_RECETAS WHERE ID_INGREDIENTE_Y_RECETA = ?";

    public void alta(Context context, IngredienteYReceta ingredienteYReceta) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase(); // la ponemos en modo de lectura y escritura

        // Conseguimos el id de la receta con la que se registrará
        Cursor fila = baseDeDatos.rawQuery(NUEVO_ID, null);
        fila.moveToFirst();
        int id = fila.getInt(0);
        ingredienteYReceta.setIdIngredienteYReceta(id); // le ponemos el id del ingrediente por registrar

        SQLiteStatement s = baseDeDatos.compileStatement(ALTA);
        s.clearBindings();
        s.bindLong(1, ingredienteYReceta.getIdIngredienteYReceta());
        s.bindLong(2, ingredienteYReceta.getIdIngrediente());
        s.bindLong(3, ingredienteYReceta.getIdReceta());
        s.executeInsert();

        s.close();
        fila.close();
        baseDeDatos.close();
    }

    public List<IngredienteYReceta> ver(Context context) {
        List<IngredienteYReceta> ver = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
        Cursor fila = baseDeDatos.rawQuery(VER, null);

        while(fila.moveToNext())
            ver.add(new IngredienteYReceta(fila.getInt(0), fila.getInt(1), fila.getInt(2)));

        fila.close();
        baseDeDatos.close();
        return ver;
    }

    public void baja(Context context, IngredienteYReceta ingredienteYReceta) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

        SQLiteStatement s = baseDeDatos.compileStatement(BAJA);
        s.clearBindings();
        s.bindLong(1, ingredienteYReceta.getIdIngredienteYReceta());
        s.executeUpdateDelete();

        s.close();
        baseDeDatos.close();
    }
}
