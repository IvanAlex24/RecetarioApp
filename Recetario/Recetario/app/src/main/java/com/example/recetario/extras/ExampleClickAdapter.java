package com.example.recetario.extras;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.recetario.R;
import com.example.recetario.dao.IngredienteDao;
import com.example.recetario.dao.IngredienteYRecetaDao;
import com.example.recetario.modelo.Ingrediente;
import com.example.recetario.modelo.IngredienteYReceta;
import com.example.recetario.modelo.Receta;

import java.util.ArrayList;

public class ExampleClickAdapter extends RecyclerView.Adapter<ExampleClickAdapter.ExampleClickViewHolder> {
    View view;

    public class ExampleClickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titulo, ingrediente;
        ImageView imagen;
        RatingBar calificacion;

        ExampleClickViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            itemView.setOnClickListener(this);
            titulo = (TextView) itemView.findViewById(R.id.tituloHolder);
            ingrediente = (TextView) itemView.findViewById(R.id.ingredienteHolder);
            imagen = (ImageView) itemView.findViewById(R.id.imagenHolder);
            calificacion = (RatingBar) itemView.findViewById(R.id.calificacionHolder);
        }

        @Override
        public void onClick(View v) {
            // The user may not set a click listener for list items, in which case our listener
            // will be null, so we need to check for this
            if (mOnEntryClickListener != null) {
                mOnEntryClickListener.onEntryClick(v, getLayoutPosition());
            }
        }
    }

    private ArrayList<Receta> mReceta;

    public ExampleClickAdapter(ArrayList<Receta> arrayList) {
        mReceta = arrayList;
    }

    @Override
    public int getItemCount() {
        return mReceta.size();
    }

    @Override
    public ExampleClickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receta_holder, parent, false);
        return new ExampleClickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExampleClickViewHolder holder, int position) {
        Receta receta = mReceta.get(position);
        String listaIngredientes = "";

        IngredienteYRecetaDao ingredienteYRecetaDao = new IngredienteYRecetaDao();
        IngredienteDao ingredienteDao =  new IngredienteDao();
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();

        // Obtener todos los ingredientes de la receta
        for(IngredienteYReceta ir : ingredienteYRecetaDao.ver(view.getContext())) {
            for(Ingrediente i : ingredienteDao.ver(view.getContext()))
                if (ir.getIdIngrediente() == i.getIdIngrediente() && ir.getIdReceta() == receta.getIdReceta())
                    ingredientes.add(i);
        }

        for(Ingrediente i : ingredientes)
            listaIngredientes += i.getNombre()+", ";
        if(listaIngredientes.length() - 2 >= 0)
            listaIngredientes = listaIngredientes.substring(0, listaIngredientes.length() - 2);

        holder.titulo.setText(receta.getTitulo());
        holder.imagen.setImageBitmap(receta.getImagen());
        holder.ingrediente.setText(listaIngredientes);
        holder.calificacion.setRating(receta.getCalificacion());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    private OnEntryClickListener mOnEntryClickListener;

    public interface OnEntryClickListener {
        void onEntryClick(View view, int position);
    }

    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
        mOnEntryClickListener = onEntryClickListener;
    }

}