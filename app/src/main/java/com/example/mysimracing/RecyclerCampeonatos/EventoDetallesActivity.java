package com.example.mysimracing.RecyclerCampeonatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysimracing.R;
import com.example.mysimracing.RecyclerCircuitos.CircuitoActivity;
import com.example.mysimracing.RecyclerSanciones.ActivitySanciones;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
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

    FirebaseFirestore firestoredb;
    FirebaseAuth mAuth;
    String nombreCamp;


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
        img_campeonato = findViewById(R.id.imageView8);
        firestoredb = FirebaseFirestore.getInstance();


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

        btn_sanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( EventoDetallesActivity.this, ActivitySanciones.class));
            }
        });

        btn_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventoDetallesActivity.this, CircuitoActivity.class));
            }
        });


    }

}