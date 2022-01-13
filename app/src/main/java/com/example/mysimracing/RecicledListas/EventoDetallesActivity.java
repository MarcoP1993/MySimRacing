package com.example.mysimracing.RecicledListas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysimracing.NormativaActivity;
import com.example.mysimracing.R;

public class EventoDetallesActivity extends AppCompatActivity {

    //EventosTorneo et = null;
    Button btn_normativa;
    Button btn_sanciones;
    Button btn_salas;
    Button btn_equipos_pilotos;
    Button btn_inscripcion;
    Button btn_clasificacion;
    Button btn_calendario;
    Button btn_configuracion;
    Button btn_cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalles);

       //txt_nombre_campeonato = findViewById(R.id.txt_nombre_detalle);
        btn_normativa = findViewById(R.id.btn_normativa);
        btn_sanciones = findViewById(R.id.btn_sanciones);
        btn_salas = findViewById(R.id.btn_salas);
        btn_equipos_pilotos = findViewById(R.id.btn_equipo_piloto);
        btn_inscripcion = findViewById(R.id.btn_inscripcion);
        btn_clasificacion = findViewById(R.id.btn_clasificacion);
        btn_calendario = findViewById(R.id.btn_calendario);
        btn_configuracion = findViewById(R.id.btn_config);
        btn_cerrar = findViewById(R.id.btn_cerrar);

        btn_normativa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventoDetallesActivity.this, NormativaActivity.class));
            }
        });


    }

    public void cerrarVentana(View view) {
        finish();
    }
}