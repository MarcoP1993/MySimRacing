package com.example.mysimracing.RecyclerCampeonatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysimracing.Clases.Inscripciones;
import com.example.mysimracing.Clases.Usuarios;
import com.example.mysimracing.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class InscripcionesActivity extends AppCompatActivity {


    Button btn_inscripcion;
    AlertDialog dialog;
    RecyclerView rv_inscrpciones;
    InscripcionesAdapter ins_adapter;

    Usuarios usuarios;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripciones);
        firestore = FirebaseFirestore.getInstance();
        btn_inscripcion = findViewById(R.id.btn_enlace_inscripcion);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Introduce tu nombre piloto/equipo, nick y correo para inscribirte");
        rv_inscrpciones = findViewById(R.id.rv_inscripciones);
        rv_inscrpciones.setLayoutManager(new LinearLayoutManager(this));
        Query query = firestore.collection("inscripciones");

        FirestoreRecyclerOptions<Inscripciones> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Inscripciones>().setQuery(query, Inscripciones.class).build();

        ins_adapter = new InscripcionesAdapter(firestoreRecyclerOptions);
        ins_adapter.notifyDataSetChanged();
        rv_inscrpciones.setAdapter(ins_adapter);

        //inflar custom_dialog
        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        firestore = FirebaseFirestore.getInstance();


        EditText nombre, nick, correo;
        nombre = view.findViewById(R.id.nombre_inscripcion);
        nick = view.findViewById(R.id.nick_inscripcion);
        correo = view.findViewById(R.id.correo_inscripcion);
        Button btn_crear = view.findViewById(R.id.btn_crear_inscripcion);
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreins = nombre.getText().toString();
                String nickins = nick.getText().toString();
                String correoins = correo.getText().toString();

                if(nombreins.isEmpty() && nickins.isEmpty() && correoins.isEmpty()){
                    nombre.setError("Rellena el campo");
                    nick.setError("Rellena el campo");
                    correo.setError("Rellena el campo");
                    Toast.makeText(InscripcionesActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();

                }else {
                    inscripcionCamp(nombreins, nickins, correoins);

                    dialog.dismiss();
                }
            }
        });
        builder.setView(view);

        dialog = builder.create();
        btn_inscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.setText("");
                nick.setText("");
                correo.setText("");
                dialog.show();
            }
        });

    }

    private void inscripcionCamp (String nombre, String nick, String correo){
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("nick", nick);
        map.put("correo", correo);

        firestore.collection("inscripciones").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(InscripcionesActivity.this, "Inscrito correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InscripcionesActivity.this, "Error al inscribirse", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ins_adapter.startListening();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("Usuarios")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("role").equals("Organizador")) {
                        btn_inscripcion.setVisibility(View.INVISIBLE);

                    } if (documentSnapshot.getString("role").equals("Jefe de Equipo")) {
                        btn_inscripcion.setVisibility(View.VISIBLE);
                    }
                    if (documentSnapshot.getString("role").equals("Piloto")) {
                        btn_inscripcion.setVisibility(View.VISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        ins_adapter.stopListening();
    }
}