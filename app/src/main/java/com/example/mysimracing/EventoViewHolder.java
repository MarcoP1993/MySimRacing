package com.example.mysimracing;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final String EXTRA_OBJETO_EVENTO = "wacho";
    public ImageView img_foto = null;
    public TextView txt_competicion = null;
    public TextView txt_ciudad = null;
    public TextView txt_plataforma = null;
    public TextView txt_empezado = null;

    ListaEventosAdapter le_adapter = null;

    public EventoViewHolder(@NonNull View itemView, ListaEventosAdapter le_adapter) {
        super(itemView);
        img_foto = (ImageView) itemView.findViewById(R.id.imageView2);
        txt_competicion = (TextView) itemView.findViewById(R.id.txt_campeonato);
        txt_ciudad = (TextView) itemView.findViewById(R.id.txt_ciudad);
        txt_plataforma = (TextView) itemView.findViewById(R.id.txt_plataforma);
        txt_empezado = (TextView) itemView.findViewById(R.id.txt_empezado);
        this.le_adapter = le_adapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int posicion = getLayoutPosition();
        EventosTorneo ev = le_adapter.getEventos().get(posicion);
        Intent intent = new Intent(le_adapter.getC(), EventoDetallesActivity.class);
        intent.putExtra(EXTRA_OBJETO_EVENTO, ev);
        le_adapter.getC().startActivity(intent);
    }
}
