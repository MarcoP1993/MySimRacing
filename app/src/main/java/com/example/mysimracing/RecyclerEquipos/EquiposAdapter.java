package com.example.mysimracing.RecyclerEquipos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysimracing.Clases.Equipos;
import com.example.mysimracing.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EquiposAdapter extends RecyclerView.Adapter<EquiposAdapter.ViewHolder> {

    Context c;
    ArrayList<Equipos> listaEquipos;

    public EquiposAdapter( ArrayList<Equipos> listaEquipos, Context context) {
        this.listaEquipos = listaEquipos;
        c = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.item_equipos,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText("Equipo: " + listaEquipos.get(position).getNombreEquipo());
        holder.pais.setText("Pais: " + listaEquipos.get(position).getPais());
        holder.jefe.setText("Jefe: " + listaEquipos.get(position).getJefe_equipo());
        holder.web.setText("Web: " + listaEquipos.get(position).getWeb());
        holder.discord.setText("Discord: " + listaEquipos.get(position).getDiscord());

        Picasso.get().load(listaEquipos.get(position).getLogo_equipo()).resize(150,150).into(holder.logo_equipo);
    }

    @Override
    public int getItemCount() {
        return listaEquipos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, pais, jefe, web, discord;
        ImageView logo_equipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_equipo_item);
            pais = itemView.findViewById(R.id.pais_equipo_item);
            jefe = itemView.findViewById(R.id.jefe_equipo_item);
            web = itemView.findViewById(R.id.web_equipo_item);
            logo_equipo = itemView.findViewById(R.id.img_logo_item);
            discord = itemView.findViewById(R.id.discord_equipo_item);
        }
    }
}
