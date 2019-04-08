package com.example.recetario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MisRecetas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_recetas);
    }

    //metodo para ir a las recetas
    public void irRecetas(View v){
        Intent i = new Intent(MisRecetas.this, Recetas.class);
        startActivity(i);
    }

}
