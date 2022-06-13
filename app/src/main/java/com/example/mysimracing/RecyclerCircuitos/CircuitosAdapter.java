package com.example.mysimracing.RecyclerCircuitos;

import android.content.Context;
import android.util.Log;
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

    public CircuitosAdapter(ArrayList<Circuitos> circuitosArrayList, Context applicationContext) {
        this.listaCircuitos = circuitosArrayList;
        c = applicationContext;
    }

    @NonNull
    @Override
    public CircuitosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.item_circuitos,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CircuitosAdapter.ViewHolder holder, int position) {
        holder.nombreCircuito.setText("Circuito: " + listaCircuitos.get(position).getNombre());
        holder.categoriaCoche.setText("Categoria: " + listaCircuitos.get(position).getCategoria());
        holder.diaCarrera.setText("Dia Carrera: " + listaCircuitos.get(position).getDiaCarrera());
        holder.horaCarrera.setText("Hora Carrera: " + listaCircuitos.get(position).getHoraCarrera());

        //cargar imagen al recyclerview con Picasso
        Picasso.get().load(listaCircuitos.get(position).getImagenCircuito()).resize(150,150).into(holder.imagen_rv);
        //String imageURI=null;
        //imageURI = listaCircuitos.get(position).getImagenCircuito();
        //Picasso.get().load(imageURI).resize(200,200).into(holder.imagen_rv);

    }

    @Override
    public int getItemCount() {
        return listaCircuitos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
