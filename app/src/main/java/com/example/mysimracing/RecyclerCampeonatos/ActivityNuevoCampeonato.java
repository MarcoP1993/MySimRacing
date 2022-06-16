package com.example.mysimracing.RecyclerCampeonatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityNuevoCampeonato extends AppCompatActivity {

    EditText nombre_campeonato;
    EditText juego_campeonato;
    EditText plataforma_campeonato;
    EditText tipo_campeonato;
    TextView fecha_campeonato;
    Button seleccionar_fechas;
    Button crearCampeonato;

    private String idUsuario;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoredb;

    //valores a guardar
    private String nombreCampeonato;
    private String rango_fechas;
    private String juego;
    private String plataforma;
    private String tipoCampeonato;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_campeonato);

        nombre_campeonato = (EditText) findViewById(R.id.edt_nomCampeonatonuevo);
        juego_campeonato = (EditText) findViewById(R.id.edt_juegoCampeonato);
        plataforma_campeonato = (EditText) findViewById(R.id.edt_platCampeonato);
        tipo_campeonato = (EditText) findViewById(R.id.edt_tipoCampeonato);
        fecha_campeonato = (TextView) findViewById(R.id.txt_rangoFechas);
        crearCampeonato = (Button) findViewById(R.id.btn_crearCampeonato);
        seleccionar_fechas = (Button) findViewById(R.id.btn_fechaCampeonato);

        firebaseAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();
        idUsuario = firebaseAuth.getCurrentUser().getEmail();


        crearCampeonato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreCampeonato = nombre_campeonato.getText().toString();
                rango_fechas = fecha_campeonato.getText().toString();
                juego = juego_campeonato.getText().toString();
                plataforma = plataforma_campeonato.getText().toString();
                tipoCampeonato = tipo_campeonato.getText().toString();
                id = firebaseAuth.getCurrentUser().getEmail();

                    if (!nombreCampeonato.isEmpty() && !juego.isEmpty() && !plataforma.isEmpty() && !tipoCampeonato.isEmpty() && !rango_fechas.isEmpty()) {

                        Campeonatos campeonato = new Campeonatos(nombreCampeonato, rango_fechas, juego, plataforma, tipoCampeonato, null, idUsuario);

                        firestoredb.collection("Campeonatos").document(idUsuario).set(campeonato).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ActivityNuevoCampeonato.this, "Campeonato Creado Correctamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ActivityNuevoCampeonato.this, "Error al crear", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }else {
                        Toast.makeText(ActivityNuevoCampeonato.this, "Completa todos los campos", Toast.LENGTH_LONG).show();
                    }

            }
        });
    }



    public void Seleccionarfecha(View view) {

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds())).build();

        datePicker.show(getSupportFragmentManager(), "Selecciona fechas");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                fecha_campeonato.setText(datePicker.getHeaderText());
            }
        });

    }
}