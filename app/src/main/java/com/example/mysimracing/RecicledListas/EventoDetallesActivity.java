package com.example.mysimracing.RecicledListas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysimracing.AjustesCampeonatoActivity;
import com.example.mysimracing.NormativaActivity;
import com.example.mysimracing.OrganizadorActivity;
import com.example.mysimracing.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

public class EventoDetallesActivity extends AppCompatActivity {

    //EventosTorneo et = null;
    TextView txt_nombre_campeonato, txt_fecha_inicio;
    CircularImageView img_campeonato;
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

        txt_nombre_campeonato = findViewById(R.id.txt_nombre_campeonato);
        txt_fecha_inicio = findViewById(R.id.txt_fecha_inicio);
        btn_normativa = findViewById(R.id.btn_normativa);
        btn_sanciones = findViewById(R.id.btn_sanciones);
        btn_salas = findViewById(R.id.btn_salas);
        btn_equipos_pilotos = findViewById(R.id.btn_equipo_piloto);
        btn_inscripcion = findViewById(R.id.btn_inscripcion);
        btn_clasificacion = findViewById(R.id.btn_clasificacion);
        btn_calendario = findViewById(R.id.btn_calendario);
        btn_configuracion = findViewById(R.id.btn_config);
        btn_cerrar = findViewById(R.id.btn_cerrar);
        img_campeonato = findViewById(R.id.imageView8);

        txt_nombre_campeonato.setText(getIntent().getStringExtra("Nombre Campeonato: ").toString());
        txt_fecha_inicio.setText(getIntent().getStringExtra("Fechas Campeonato: ").toString());

        btn_normativa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventoDetallesActivity.this, NormativaActivity.class));
            }
        });

        btn_inscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventoDetallesActivity.this, InscripcionesActivity.class));
            }
        });

        btn_configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventoDetallesActivity.this, AjustesCampeonatoActivity.class));
            }
        });

        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OrganizadorActivity.class));
                finish();
            }
        });

    }

}