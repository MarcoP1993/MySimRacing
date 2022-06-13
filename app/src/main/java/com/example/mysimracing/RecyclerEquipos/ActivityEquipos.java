package com.example.mysimracing.RecyclerEquipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mysimracing.Clases.Equipos;
import com.example.mysimracing.R;
import com.example.mysimracing.RecyclerCircuitos.CircuitoActivity;
import com.example.mysimracing.RecyclerCircuitos.CircuitosNuevoActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ActivityEquipos extends AppCompatActivity {

    private Button btn_nuevo_equipo;
    
    private RecyclerView rv_equipos;
    private EquiposAdapter equiposAdapter;
    private ArrayList<Equipos> equiposArrayList = new ArrayList<>();
    
    private FirebaseFirestore firestoredb;
    private FirebaseStorage storage;
    private CollectionReference collectionReference;
    private String idEquipo;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);
        
        btn_nuevo_equipo = findViewById(R.id.btn_nuevo_equipo);

        rv_equipos = findViewById(R.id.rv_equipos);
        rv_equipos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firestoredb = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        collectionReference = firestoredb.collection("Equipos");
        idEquipo = firebaseAuth.getCurrentUser().getEmail();
        
        collectionReference.whereEqualTo("id", idEquipo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot listaequipos : queryDocumentSnapshots){
                    equiposArrayList.add(listaequipos.toObject(Equipos.class));
                    //listaequipos.getId();
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                equiposAdapter = new EquiposAdapter(equiposArrayList, getApplicationContext());
                rv_equipos.setAdapter(equiposAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEquipos.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });

        btn_nuevo_equipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityEquipos.this, NuevoEquipoActivity.class));
            }
        });


    }
    
}