package com.example.mysimracing.RecicledListas;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class EventoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final String EXTRA_OBJETO_EVENTO = "wacho";
    public static final String EXTRA_OBJETO_IMAGEN = "imagenesDelCampeonatos";
    public CircularImageView img_foto = null;
    public TextView txt_competicion = null;
    public TextView txt_juego = null;
    public TextView txt_plataforma = null;
    public TextView txt_fechas = null;
    ListaEventosAdapter le_adapter = null;

    public EventoViewHolder(@NonNull View itemView, ListaEventosAdapter le_adapter) {
        super(itemView);
        img_foto = (CircularImageView) itemView.findViewById(R.id.imageView2);
        txt_competicion = (TextView) itemView.findViewById(R.id.txt_campeonato);
        txt_juego = (TextView) itemView.findViewById(R.id.txt_juego);
        txt_plataforma = (TextView) itemView.findViewById(R.id.txt_plataforma);
        txt_fechas = (TextView) itemView.findViewById(R.id.txt_fechas);
        this.le_adapter = le_adapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int posicion = getLayoutPosition();
        List<Campeonatos> listaCampeonatos = this.le_adapter.getCampeonatos();
        Campeonatos campeonato = listaCampeonatos.get(posicion);
        Intent intent = new Intent(le_adapter.getC(), EventoDetallesActivity.class);
        intent.putExtra(EXTRA_OBJETO_EVENTO, campeonato);
        le_adapter.getC().startActivity(intent);
    }
}
