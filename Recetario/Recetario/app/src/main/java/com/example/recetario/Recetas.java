package com.example.recetario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.recetario.dao.RecetaDao;
import com.example.recetario.extras.Constante;
import com.example.recetario.extras.ExampleAdapter;
import com.example.recetario.extras.ExampleClickAdapter;
import com.example.recetario.modelo.Receta;

import java.util.ArrayList;

public class Recetas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);
        Constante.recetaActual = null;

        // Setting up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Getting your ArrayList - this will be up to you
        RecetaDao recetaDao = new RecetaDao();
        ArrayList<Receta> recetas = new ArrayList<>();
        for(Receta r : recetaDao.ver(this))
            if(r.getIdUsuario() == Constante.usuarioActual.getIdUsuario())
                recetas.add(r);
        Constante.recetasUsuario = recetas;

        // RecyclerView with a click listener
        ExampleClickAdapter clickAdapter = new ExampleClickAdapter(recetas);
        clickAdapter.setOnEntryClickListener(new ExampleClickAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position) {
                // stuff that will happen when a list item is clicked
                Constante.recetaActual = Constante.recetasUsuario.get(position);
                Intent i = new Intent(Recetas.this, MiReceta.class);
                startActivity(i);
            }
        });
        recyclerView.setAdapter(clickAdapter);
    }


    //Aun no aplico estos metodos queria ver como hacer para que
    /*
    * 1. Agregar un boton flotante
    * 2. Averiguar como usar el layout ScrollView
    * */
    //metodo para ir a MiReceta
    public void irMiReceta(View v){
        Intent i = new Intent(Recetas.this,MiReceta.class);
        startActivity(i);
    }
    //metodo para ir a NuevaReceta
    public void irNuevaReceta(View v){
        Intent i = new Intent(Recetas.this,NuevaReceta.class);
        startActivity(i);
    }
}
