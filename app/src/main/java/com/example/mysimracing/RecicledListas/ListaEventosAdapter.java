package com.example.mysimracing.RecicledListas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.R;

import java.util.ArrayList;

public class ListaEventosAdapter extends RecyclerView.Adapter<EventoViewHolder> {
    //atributos
    private Context c = null;
    private ArrayList<Campeonatos> campeonatos;
    private LayoutInflater le_inflater;

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public ArrayList<Campeonatos> getCampeonatos() {
        return campeonatos;
    }

    public void setEventos(ArrayList<Campeonatos> campeonatos) {
        this.campeonatos = campeonatos;
    }

    public ListaEventosAdapter(Context context, ArrayList<Campeonatos> campeonatos) {
        this.c = context;
        this.campeonatos = campeonatos;
        le_inflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = le_inflater.inflate(R.layout.item_lista_eventos, parent, false);
        EventoViewHolder evh = new EventoViewHolder(mItemView, this);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Campeonatos campeonatoSeleccionado = campeonatos.get(position);
        holder.txt_competicion.setText("Nombre Competicion: " + campeonatoSeleccionado.getNombreCampeonato());
        holder.txt_juego.setText("Videojuego: " + campeonatoSeleccionado.getJuego());
        holder.txt_plataforma.setText("Plataforma: " + campeonatoSeleccionado.getPlataforma());
        holder.txt_fechas.setText("Fecha Competicion: " + campeonatoSeleccionado.getRango_fechas());

    }

    @Override
    public int getItemCount() {
        return campeonatos.size();
    }
}
