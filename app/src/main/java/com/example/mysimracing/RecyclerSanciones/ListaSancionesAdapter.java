package com.example.mysimracing.RecyclerSanciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysimracing.Clases.Sanciones;
import com.example.mysimracing.R;

import java.util.ArrayList;

public class ListaSancionesAdapter extends RecyclerView.Adapter<ListaSancionesAdapter.SancionesViewHolder>{

    Context context;
    ArrayList<Sanciones> sanciones;

    public ListaSancionesAdapter(Context context, ArrayList<Sanciones> sanciones) {
        this.context = context;
        this.sanciones = sanciones;
    }

    @NonNull
    @Override
    public ListaSancionesAdapter.SancionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_sanciones, parent, false);


        return new SancionesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaSancionesAdapter.SancionesViewHolder holder, int position) {

        Sanciones sanc = sanciones.get(position);

        holder.piloto.setText("Piloto: " + sanc.getPiloto());
        holder.equipo.setText("Equipo: " + sanc.getEquipo());
        holder.circuito.setText("Circuito: " + sanc.getCircuito());
        holder.tiempo.setText("Tpo. Sancion: " + sanc.getTiempo());
        holder.descripcion.setText("Motivo: " + sanc.getDescripcion());
        holder.salas.setText("Sala: " + sanc.getSalas());

    }

    @Override
    public int getItemCount() {
        return sanciones.size();
    }

    public static class SancionesViewHolder extends RecyclerView.ViewHolder{

        TextView equipo, piloto, circuito, tiempo, descripcion, salas;

        public SancionesViewHolder(@NonNull View itemView) {
            super(itemView);
            piloto = itemView.findViewById(R.id.txt_sanc_piloto);
            equipo = itemView.findViewById(R.id.txt_sanc_equipo);
            circuito = itemView.findViewById(R.id.txt_sanc_circ);
            tiempo = itemView.findViewById(R.id.txt_tpo_sancion);
            descripcion = itemView.findViewById(R.id.txt_desc_sanc);
            salas = itemView.findViewById(R.id.txt_sanc_sala);
        }
    }
}
