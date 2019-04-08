package com.example.recetario.extras;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recetario.R;
import com.example.recetario.modelo.Ingrediente;

import java.util.ArrayList;

public class IngredienteAdapter extends RecyclerView.Adapter<IngredienteAdapter.IngredienteViewHolder> {

    public class IngredienteViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        IngredienteViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }

    }

    private ArrayList<Ingrediente> mIngredientes;

    public IngredienteAdapter(ArrayList<Ingrediente> arrayList) {
        mIngredientes = arrayList;
    }

    @Override
    public int getItemCount() {
        return mIngredientes.size();
    }

    @Override
    public IngredienteAdapter.IngredienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new IngredienteAdapter.IngredienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredienteAdapter.IngredienteViewHolder holder, int position) {
        Ingrediente ingrediente = mIngredientes.get(position);

        holder.text.setText(ingrediente.getNombre());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}