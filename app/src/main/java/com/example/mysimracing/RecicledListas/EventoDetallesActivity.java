package com.example.mysimracing.RecicledListas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mysimracing.R;

public class EventoDetallesActivity extends AppCompatActivity {

    EventosTorneo et = null;
    TextView txt_nombre_campeonato;
    TextView txt_ciudad_campeonato;
    TextView txt_plataforma_campeonato;
    TextView txt_campeonato_comenzado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalles);

        txt_nombre_campeonato = findViewById(R.id.txt_nombre_detalle);
        txt_ciudad_campeonato = findViewById(R.id.txt_ciudad_detalle);
        txt_plataforma_campeonato = findViewById(R.id.txt_plataforma_detalle);
        txt_campeonato_comenzado = findViewById(R.id.txt_competicion_empezada);

        Intent intent = getIntent();
        if(intent != null){
            et = (EventosTorneo) intent.getSerializableExtra(EventoViewHolder.EXTRA_OBJETO_EVENTO);
            String nombre_campeonato = et.getNombreTorneo();
            String ciudad_campeonato = et.getCiudad();
            String plataforma_campeonato = et.getPlataforma();
            Boolean campeonato_comenzado = et.getCompitiendo();
            txt_nombre_campeonato.setText("Campeonato: " + nombre_campeonato);
            txt_ciudad_campeonato.setText("Ciudad: " + ciudad_campeonato);
            txt_plataforma_campeonato.setText("Plataforma: " + plataforma_campeonato);
            txt_campeonato_comenzado.setText((String.valueOf("Comenzado: " + campeonato_comenzado)));
        }
    }

    public void cerrarVentana(View view) {
        finish();
    }
}