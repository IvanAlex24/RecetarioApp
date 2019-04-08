package com.example.recetario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.recetario.base.AdminSQLiteOpenHelper;
import com.example.recetario.dao.IngredienteDao;
import com.example.recetario.dao.IngredienteYRecetaDao;
import com.example.recetario.dao.RecetaDao;
import com.example.recetario.extras.Constante;
import com.example.recetario.extras.IngredienteAdapter;
import com.example.recetario.modelo.Ingrediente;
import com.example.recetario.modelo.IngredienteYReceta;
import com.example.recetario.modelo.Receta;

import java.util.ArrayList;

public class MiReceta extends AppCompatActivity {
    Receta receta = Constante.recetaActual;
    // Elementos de la vista
    TextView tvTitulo, tvProcedimiento;
    ImageView ivImagen;
    RatingBar rbCalificacion;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_receta);

        // Guardar en objetos los elementos de la vista
        recyclerView = (RecyclerView) findViewById(R.id.rvIngredientes);
        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvProcedimiento = (TextView) findViewById(R.id.tvProcedimiento);
        ivImagen = (ImageView) findViewById(R.id.ivImagen);
        rbCalificacion = (RatingBar) findViewById(R.id.rbCalificacion);

        // Setting up the RecyclerView
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Getting your ArrayList - this will be up to you
        IngredienteDao ingredienteDao = new IngredienteDao();
        IngredienteYRecetaDao ingredienteYRecetaDao = new IngredienteYRecetaDao();
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        // Guardar en el arraylist todos los ingredientes que sean de la receta
        for(IngredienteYReceta ir : ingredienteYRecetaDao.ver(this))
            for(Ingrediente i : ingredienteDao.ver(this))
                if (ir.getIdIngrediente() == i.getIdIngrediente() && ir.getIdReceta() == receta.getIdReceta())
                    ingredientes.add(i);

        // Standard RecyclerView implementation
        IngredienteAdapter adapter = new IngredienteAdapter(ingredientes);
        recyclerView.setAdapter(adapter);

        tvTitulo.setText(receta.getTitulo());
        tvProcedimiento.setText(receta.getProcedimiento());
        rbCalificacion.setRating(receta.getCalificacion());
        ivImagen.setImageBitmap(receta.getImagen());

    }

    public void eliminar(View v){
        final RecetaDao recetaDao = new RecetaDao();

        new AlertDialog.Builder(this)
            .setMessage("¿Quieres borrar esta receta?")
            .setCancelable(false)
            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    recetaDao.baja(MiReceta.this, receta);
                    Intent i = new Intent(MiReceta.this, Recetas.class);
                    startActivity(i);
                }
            })
            .setNegativeButton("No", null)
            .show();
    }

    public void editar(View v){
        Intent i = new Intent(MiReceta.this, EditarReceta.class);
        startActivity(i);
    }

}
