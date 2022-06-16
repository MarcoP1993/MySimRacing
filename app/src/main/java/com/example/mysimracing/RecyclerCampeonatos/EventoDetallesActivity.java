package com.example.mysimracing.RecyclerCampeonatos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysimracing.Authentication.LoginActivity;
import com.example.mysimracing.MenuOpciones.PerfilUsuarioActivity;
import com.example.mysimracing.R;
import com.example.mysimracing.RecyclerCircuitos.CircuitoActivity;
import com.example.mysimracing.RecyclerSanciones.ActivitySanciones;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    Button btn_eliminar;

    FirebaseFirestore firestoredb;
    FirebaseAuth mAuth;
    String nombreCamp;
    private String idUsuario;


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
        btn_eliminar = findViewById(R.id.btn_eliminar);
        img_campeonato = findViewById(R.id.imageView8);

        mAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();
        idUsuario = mAuth.getCurrentUser().getEmail();

        DocumentReference docRef = firestoredb.collection("Usuarios").document(idUsuario);
        docRef.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                txt_nombre_campeonato.setText("Nombre Campeonato" + documentSnapshot.getString("nombreCampeonato"));
                txt_fecha_inicio.setText("Fechas Campeonato" + documentSnapshot.getString("rango_fechas"));
            }
        });

        //txt_nombre_campeonato.setText(getIntent().getStringExtra("Nombre Campeonato: ").toString());
        //txt_fecha_inicio.setText(getIntent().getStringExtra("Fechas Campeonato: ").toString());


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

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EventoDetallesActivity.this);
                builder.setMessage("¿Estas seguro de que quieres eliminar el campeonato?\n " +
                        "No habrá vuelta atrás")
                        .setTitle("Eliminar Campeonato");
                
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firestoredb.collection("Campeonatos").document(idUsuario).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(EventoDetallesActivity.this, "Campeonato eliminado", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EventoDetallesActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


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