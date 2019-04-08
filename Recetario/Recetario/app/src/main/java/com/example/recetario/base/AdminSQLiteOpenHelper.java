package com.example.recetario.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USUARIOS(ID_USUARIO INT PRIMARY KEY, USUARIO TEXT, CONTRASENA TEXT )");
        sqLiteDatabase.execSQL("CREATE TABLE RECETAS(ID_RECETA INT PRIMARY KEY, ID_USUARIO INT ,TITULO TEXT, PROCEDIMIENTO TEXT, CALIFICACION INT, IMAGEN LONGBLOB , FOREIGN KEY(ID_USUARIO) REFERENCES USUARIOS(ID_USUARIO))");
        sqLiteDatabase.execSQL("CREATE TABLE INGREDIENTES(ID_INGREDIENTE INT PRIMARY KEY, NOMBRE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE INGREDIENTES_Y_RECETAS(ID_INGREDIENTE_Y_RECETA INT PRIMARY KEY, ID_INGREDIENTE INT, ID_RECETA INT, FOREIGN KEY(ID_INGREDIENTE) REFERENCES INGREDIENTES(ID_INGREDIENTE), FOREIGN KEY(ID_RECETA) REFERENCES RECETAS(ID_RECETA))");
        sqLiteDatabase.execSQL("CREATE TABLE ACTIVO(ID_USUARIO)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
