package com.example.mysimracing.RecicledListas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysimracing.R;

import java.util.ArrayList;

public class ListaEventosAdapter extends RecyclerView.Adapter<EventoViewHolder> {
    //atributos
    private Context c = null;
    private ArrayList<EventosTorneo> eventos;
    private LayoutInflater le_inflater;

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public ArrayList<EventosTorneo> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<EventosTorneo> eventos) {
        this.eventos = eventos;
    }

    public ListaEventosAdapter(Context context, ArrayList<EventosTorneo> eventos) {
        this.c = context;
        this.eventos = eventos;
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
        EventosTorneo eventoSeleccionado = eventos.get(position);
        holder.txt_competicion.setText("Nombre Competicion: " + eventoSeleccionado.getNombreTorneo());
        holder.txt_ciudad.setText("Ciudad Competicion: " + eventoSeleccionado.getCiudad());
        holder.txt_plataforma.setText("Plataforma: " + eventoSeleccionado.getPlataforma());
        holder.txt_empezado.setText("Competicion empezada: " + eventoSeleccionado.getCompitiendo());

    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
}
