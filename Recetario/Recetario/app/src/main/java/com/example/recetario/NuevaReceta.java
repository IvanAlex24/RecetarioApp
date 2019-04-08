package com.example.recetario;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.recetario.dao.IngredienteDao;
import com.example.recetario.dao.IngredienteYRecetaDao;
import com.example.recetario.dao.RecetaDao;
import com.example.recetario.extras.Constante;
import com.example.recetario.modelo.Ingrediente;
import com.example.recetario.modelo.IngredienteYReceta;
import com.example.recetario.modelo.Receta;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NuevaReceta extends AppCompatActivity {
    EditText etTitulo, etProcedimiento;
    ImageView ivImagen;
    Button botonImagen, botonGuardar;
    RatingBar rbCalificacion;
    LinearLayout llIngredientes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);

        etTitulo = (EditText) findViewById(R.id.etTitulo);
        etProcedimiento = (EditText) findViewById(R.id.etProcedimiento);
        botonImagen = (Button)findViewById(R.id.botonImagen);
        botonGuardar = (Button)findViewById(R.id.botonGuardar);
        ivImagen = (ImageView)findViewById(R.id.imagen);
        rbCalificacion = (RatingBar)findViewById(R.id.rbCalificacion);
        llIngredientes = (LinearLayout)findViewById(R.id.llIngredientes);

        botonImagen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }});

        botonGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Delay
                botonGuardar.setEnabled(false);

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                botonGuardar.setEnabled(true);
                            }
                        });
                    }
                }, 5000);

                // Obtener información ingresada
                String titulo = etTitulo.getText().toString();
                String procedimiento = etProcedimiento.getText().toString();
                int calificacion = (int)rbCalificacion.getRating();
                ArrayList<Ingrediente> ingredientes = new ArrayList<>();
                boolean sinVacios = true;
                if(llIngredientes.getChildCount() == 0)
                    sinVacios = false;
                else
                    for(int i=0; i<llIngredientes.getChildCount(); i++) {
                        EditText editText = (EditText) llIngredientes.getChildAt(i);
                        if(!editText.getText().toString().equals(""))
                            ingredientes.add(new Ingrediente(0, editText.getText().toString()));
                        else {
                            sinVacios = false;
                            break;
                        }
                    }

                if(!(titulo.equals("") || procedimiento.equals("")) && sinVacios) {
                    // Guardar en un modelo
                    Bitmap imagen =((BitmapDrawable)ivImagen.getDrawable()).getBitmap();
                    Receta receta = new Receta(0, Constante.usuarioActual.getIdUsuario(), titulo, procedimiento, calificacion, imagen);

                    //Guardar en la base de datos
                    RecetaDao recetaDao = new RecetaDao();
                    recetaDao.alta(NuevaReceta.this, receta);

                    // PRUEBAS PARA EL RECYCLERVIEW DE LOS INGREDIENTES.
/*
                    Ingrediente ingrediente1 = new Ingrediente(0, "Huevo");
                    Ingrediente ingrediente2 = new Ingrediente(0, "Arroz");
                    ingredienteDao.alta(NuevaReceta.this, ingrediente1);
                    ingredienteDao.alta(NuevaReceta.this, ingrediente2);
                    IngredienteYReceta ingredienteYReceta1 = new IngredienteYReceta(0, ingrediente1.getIdIngrediente(), receta.getIdReceta());
                    IngredienteYReceta ingredienteYReceta2 = new IngredienteYReceta(0, ingrediente2.getIdIngrediente(), receta.getIdReceta());
                    ingredienteYRecetaDao.alta(NuevaReceta.this, ingredienteYReceta1);
                    ingredienteYRecetaDao.alta(NuevaReceta.this, ingredienteYReceta2);
*/

                    IngredienteDao ingredienteDao = new IngredienteDao();
                    IngredienteYRecetaDao ingredienteYRecetaDao = new IngredienteYRecetaDao();

                    for(Ingrediente i : ingredientes) {
                        ingredienteDao.alta(NuevaReceta.this, i);
                        IngredienteYReceta ingredienteYReceta = new IngredienteYReceta(0, i.getIdIngrediente(), receta.getIdReceta());
                        ingredienteYRecetaDao.alta(NuevaReceta.this, ingredienteYReceta);
                    }

                    Toast.makeText(NuevaReceta.this, "Receta guardada", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(NuevaReceta.this, Recetas.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(NuevaReceta.this, "Llena todos los campos, por favor", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            System.out.println("text target Uri"+targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                ivImagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void addLine(View v) {
        // add edittext
        EditText et = new EditText(this);
        LinearLayout.LayoutParams p = new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        et.setLayoutParams(p);
        et.setHint("Ingrediente #" + (llIngredientes.getChildCount()+1));
        llIngredientes.addView(et);
        et.requestFocus();
    }

    public void removeLine(View v) {
        llIngredientes.removeViewAt(llIngredientes.getChildCount()-1);
    }
}
