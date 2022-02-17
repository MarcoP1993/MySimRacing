package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysimracing.Clases.Sanciones;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityNuevaSancion extends AppCompatActivity {

    EditText sancion_eq_pi;
    EditText circuito_sancion;
    EditText tiempo_sancion;
    EditText descripcion_sancion;
    EditText sala_carrera;
    Button crearSancion;

    private FirebaseFirestore firestoredb;

    //valores a guardar
    private String sancionEquipoPiloto;
    private String sancionCircuito;
    private String sancionTiempo;
    private String sancionDescripcion;
    private String sancionSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_sancion);

        sancion_eq_pi = (EditText) findViewById(R.id.edt_sancion_equiPilo);
        circuito_sancion = (EditText) findViewById(R.id.edt_circuito_sancion);
        tiempo_sancion = (EditText) findViewById(R.id.edt_tiempo_sancion);
        descripcion_sancion = (EditText) findViewById(R.id.edt_descripcion_sancion);
        sala_carrera = (EditText) findViewById(R.id.edt_sala_sancion);

        firestoredb = FirebaseFirestore.getInstance();

        crearSancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sancionEquipoPiloto = sancion_eq_pi.getText().toString();
                sancionCircuito = circuito_sancion.getText().toString();
                sancionTiempo = tiempo_sancion.getText().toString();
                sancionDescripcion = descripcion_sancion.getText().toString();
                sancionSala = sala_carrera.getText().toString();

                if(!sancionEquipoPiloto.isEmpty() && !sancionCircuito.isEmpty() && !sancionTiempo.isEmpty() && !sancionDescripcion.isEmpty() && !sancionSala.isEmpty()){

                    Sanciones sanciones = new Sanciones(sancionEquipoPiloto,sancionCircuito, sancionTiempo, sancionDescripcion, sancionSala);

                    firestoredb.collection("Sanciones").add(sanciones).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(ActivityNuevaSancion.this, "Sancion creada correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ActivityNuevaSancion.this, "Error al crear la sancion", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(ActivityNuevaSancion.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}