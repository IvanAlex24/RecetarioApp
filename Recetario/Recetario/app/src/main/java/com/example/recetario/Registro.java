package com.example.recetario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recetario.dao.UsuarioDao;
import com.example.recetario.modelo.Usuario;

public class Registro extends AppCompatActivity {
    // Elementos de la actividad
    EditText usuarioEdit, contrasenaEdit, repetirContrasenaEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        // Vincular objetos con el view
        usuarioEdit = findViewById(R.id.etUsuario);
        contrasenaEdit = findViewById(R.id.etContra);
        repetirContrasenaEdit = findViewById(R.id.etRepContra);
    }

    //metodo que te regresa al inicio
    public void irInicio(View v){
        if(!(usuarioEdit.getText().toString().equals("") || contrasenaEdit.getText().toString().equals("") || repetirContrasenaEdit.getText().toString().equals(""))) { // Que no haya algún campo vacío
            if(contrasenaEdit.getText().toString().equals(repetirContrasenaEdit.getText().toString())) { // Que las contraseñas coincidan
                // Guardar en un objeto del modelo
                Usuario usuario = new Usuario(0, usuarioEdit.getText().toString(), contrasenaEdit.getText().toString());
                UsuarioDao usuarioDao = new UsuarioDao();

                usuarioDao.alta(this, usuario); // Registrar al usuario en la base de datos
                Toast.makeText(Registro.this, "Registrado con éxito", Toast.LENGTH_LONG).show();

                Intent i = new Intent(Registro.this, Inicio.class);
                startActivity(i);

            }
            else {
                Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(Registro.this, "Llena todos los campos", Toast.LENGTH_LONG).show();
        }

    }
    //
}
