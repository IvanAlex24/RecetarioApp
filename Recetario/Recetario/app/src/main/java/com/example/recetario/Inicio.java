package com.example.recetario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recetario.dao.UsuarioDao;
import com.example.recetario.extras.Constante;
import com.example.recetario.modelo.Usuario;

import java.util.List;

public class Inicio extends AppCompatActivity {
    // Elementos de la actividad
    private EditText usuarioEdit, contrasenaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Constante.usuarioActual = null;

        // Vincular objetos con el view
        usuarioEdit = findViewById(R.id.etUsuario);
        contrasenaEdit = findViewById(R.id.etContra);
    }

    //metodo para ir a Registro
    public void irRegistro(View v){
        Intent i = new Intent(Inicio.this, Registro.class);
        startActivity(i);
    }

    //Metodo para ir a mis recetas
    public void irMisRecetas(View v){
        Usuario usuario = new Usuario(0, usuarioEdit.getText().toString(), contrasenaEdit.getText().toString());
        UsuarioDao usuarioDao = new UsuarioDao();
        List<Usuario> usuarios = usuarioDao.busca(this, usuario);

        if(!usuarios.isEmpty()) {
            Intent i = new Intent(Inicio.this, MisRecetas.class);
            Constante.usuarioActual = usuario;
            startActivity(i);
        }
        else {
            Toast.makeText(Inicio.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }

}
