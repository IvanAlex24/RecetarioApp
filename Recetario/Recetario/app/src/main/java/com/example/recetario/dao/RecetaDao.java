package com.example.recetario.dao;
//https://es.stackoverflow.com/questions/74332/guardar-imagenes-en-sqlite-android
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.recetario.base.AdminSQLiteOpenHelper;
import com.example.recetario.modelo.IngredienteYReceta;
import com.example.recetario.modelo.Receta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RecetaDao {
    final private String BASE = "RECETARIO";
    final private String NUEVO_ID = "SELECT IFNULL(MAX(ID_RECETA),0)+1 FROM RECETAS";
    final private String ALTA = "INSERT INTO RECETAS VALUES(?,?,?,?,?,?)";
    final private String VER = "SELECT * FROM RECETAS";
    final private String BAJA = "DELETE FROM RECETAS WHERE ID_RECETA = ?";
    final private String CAMBIO = "UPDATE RECETAS SET TITULO = ?, PROCEDIMIENTO = ?, CALIFICACION = ?, IMAGEN = ? WHERE ID_RECETA = ?";

    public void alta(Context context, Receta receta) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase(); // la ponemos en modo de lectura y escritura

        // Conseguimos el id de la receta con la que se registrará
        Cursor fila = baseDeDatos.rawQuery(NUEVO_ID, null);
        fila.moveToFirst();
        int id = fila.getInt(0);
        receta.setIdReceta(id); // le ponemos el id a la receta por registrar

        // Convertir el bitmap de la receta a blob
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        receta.getImagen().compress(Bitmap.CompressFormat.JPEG, 50 , baos);
        byte[] blob = baos.toByteArray();

        SQLiteStatement s = baseDeDatos.compileStatement(ALTA);
        s.clearBindings();
        s.bindLong(1, receta.getIdReceta());
        s.bindLong(2, receta.getIdUsuario());
        s.bindString(3, receta.getTitulo());
        s.bindString(4, receta.getProcedimiento());
        s.bindLong(5, receta.getCalificacion());
        s.bindBlob(6, blob);
        s.executeInsert();

        s.close();
        fila.close();
        baseDeDatos.close();
    }

    public List<Receta> ver(Context context) {
        List<Receta> ver = new ArrayList<>();
        Bitmap bitmap = null;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
        Cursor fila = baseDeDatos.rawQuery(VER, null);

        while(fila.moveToNext()) {
            byte[] blob = fila.getBlob(5);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
            ver.add(new Receta(fila.getInt(0), fila.getInt(1), fila.getString(2), fila.getString(3), fila.getInt(4), bitmap));
        }

        fila.close();
        baseDeDatos.close();
        return ver;
    }

    public void baja(Context context, Receta receta) {
        IngredienteYRecetaDao ingredienteYRecetaDao = new IngredienteYRecetaDao();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

        // Borrar primero las incidencias de llaves foráneas de la receta a borrar
        for(IngredienteYReceta ir : ingredienteYRecetaDao.ver(context))
            if(ir.getIdReceta() == receta.getIdReceta())
                ingredienteYRecetaDao.baja(context, ir);

        SQLiteStatement s = baseDeDatos.compileStatement(BAJA);
        s.clearBindings();
        s.bindLong(1, receta.getIdReceta());
        s.executeUpdateDelete(); // Borrar

        s.close();
        baseDeDatos.close();
    }

    public void cambio(Context context, Receta receta) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();

        // Convertir el bitmap de la receta a blob
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        receta.getImagen().compress(Bitmap.CompressFormat.JPEG, 50 , baos);
        byte[] blob = baos.toByteArray();

        SQLiteStatement s = baseDeDatos.compileStatement(CAMBIO);
        s.clearBindings();
        s.bindString(1, receta.getTitulo());
        s.bindString(2, receta.getProcedimiento());
        s.bindLong(3, receta.getCalificacion());
        s.bindBlob(4, blob);
        s.bindLong(5, receta.getIdReceta());
        s.executeUpdateDelete(); // Cambiar

        s.close();
        baseDeDatos.close();
    }
}