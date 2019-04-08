package com.example.recetario.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.recetario.Registro;
import com.example.recetario.base.AdminSQLiteOpenHelper;
import com.example.recetario.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    final private String BASE = "RECETARIO";
    final private String NUEVO_ID = "SELECT IFNULL(MAX(ID_USUARIO),0)+1 FROM USUARIOS";
    final private String ALTA = "INSERT INTO USUARIOS VALUES(?,?,?)";
    final private String BUSCA = "SELECT * FROM USUARIOS WHERE USUARIO = ? AND CONTRASENA = ?";

    public void alta(Context context, Usuario usuario) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase(); // la ponemos en modo de lectura y escritura

        // Conseguimos el id del usuario que se registrará
        Cursor fila = baseDeDatos.rawQuery(NUEVO_ID, null);
        fila.moveToFirst();
        int id = fila.getInt(0);
        usuario.setIdUsuario(id);

        SQLiteStatement s = baseDeDatos.compileStatement(ALTA);
        s.clearBindings();
        s.bindLong(1, usuario.getIdUsuario());
        s.bindString(2, usuario.getUsuario());
        s.bindString(3, usuario.getContrasena());
        s.executeInsert();

        s.close();
        fila.close();
        baseDeDatos.close();
    }

    public List<Usuario> busca(Context context, Usuario usuario) {

        List<Usuario> ver = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, BASE, null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
        Cursor fila = baseDeDatos.rawQuery(BUSCA, new String[] {usuario.getUsuario(), usuario.getContrasena()});

        while(fila.moveToNext()) {
            usuario.setIdUsuario(fila.getInt(0));
            ver.add(new Usuario(fila.getInt(0), fila.getString(1), fila.getString(2)));
        }

        fila.close();
        baseDeDatos.close();
        return ver;
    }

//    public List<Usuario> ver(Context context) {
//        List<Usuario> ver = new ArrayList<>();
//
//        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "recetario", null, 1);
//        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
//        Cursor fila = baseDeDatos.rawQuery("SELECT * FROM USUARIOS", null);
//        while(fila.moveToNext())
//            ver.add(new Usuario(fila.getInt(0), fila.getString(1), fila.getString(2)));
//        baseDeDatos.close();
//        return ver;
//    }
}

