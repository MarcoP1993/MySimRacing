package com.example.mysimracing.RecyclerCircuitos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysimracing.Clases.Circuitos;
import com.example.mysimracing.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CircuitosAdapter extends RecyclerView.Adapter<CircuitosAdapter.ViewHolder> {

    Context c;
    ArrayList<Circuitos> listaCircuitos;

    public CircuitosAdapter(Context c, ArrayList<Circuitos> listaCircuitos) {
        this.c = c;
        this.listaCircuitos = listaCircuitos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circuitos,parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Circuitos circuitos = listaCircuitos.get(position);
        holder.nombreCircuito.setText("Circuito: " + circuitos.getNombre());
        holder.categoriaCoche.setText("Categoria: " + circuitos.getCategoria());
        holder.diaCarrera.setText("Dia Carrera: " + circuitos.getDiaCarrera());
        holder.horaCarrera.setText("Hora Carrera: " + circuitos.getHoraCarrera());

        //cargar imagen al recyclerview con Picasso

        String imageURI=null;
        imageURI = circuitos.getImagenCircuito();
        Picasso.get().load(imageURI).into(holder.imagen_rv);

    }

    @Override
    public int getItemCount() {
        return listaCircuitos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen_rv;
        TextView nombreCircuito, categoriaCoche, diaCarrera, horaCarrera;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen_rv= itemView.findViewById(R.id.img_circuito_rv);
            nombreCircuito =itemView.findViewById(R.id.txt_circuito);
            categoriaCoche = itemView.findViewById(R.id.txt_categoria);
            diaCarrera = itemView.findViewById(R.id.txt_dia);
            horaCarrera = itemView.findViewById(R.id.txt_hora);

        }
    }
}
