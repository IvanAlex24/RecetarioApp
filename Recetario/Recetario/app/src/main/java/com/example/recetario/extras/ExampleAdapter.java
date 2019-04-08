package com.example.recetario.extras;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recetario.R;
import com.example.recetario.modelo.Receta;

import java.io.File;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        ImageView imagen;
        RatingBar calificacion;

        ExampleViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.tituloHolder);
            imagen = (ImageView) itemView.findViewById(R.id.imagenHolder);
            calificacion = (RatingBar) itemView.findViewById(R.id.calificacionHolder);
        }
    }

    private ArrayList<Receta> mRecetas;

    public ExampleAdapter(ArrayList<Receta> arrayList) {
        mRecetas = arrayList;
    }

    @Override
    public int getItemCount() {
        return mRecetas.size();
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receta_holder, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Receta receta = mRecetas.get(position);

        holder.titulo.setText(receta.getTitulo());
        holder.imagen.setImageBitmap(receta.getImagen());
        holder.calificacion.setRating(receta.getCalificacion());
//        Glide.with(holder.imagen.getContext())
//                .load(new File(receta.getImagen()))
//                .into(holder.imagen);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}