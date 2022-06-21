package com.example.mysimracing.RecyclerCircuitos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.Clases.Circuitos;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class CircuitoActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseStorage storage;
    CollectionReference reference;
    FirebaseAuth mAuth;

    private RecyclerView rv_circuitos;
    private CircuitosAdapter circuitosAdapter;
    private ArrayList<Circuitos> circuitosArrayList = new ArrayList<>();

    private Button btn_nuevo_circuito;
    private String idCircuito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuito);

        btn_nuevo_circuito = findViewById(R.id.btn_nuevo_circuito);

        rv_circuitos = (RecyclerView) findViewById(R.id.rv_circuitos);
        rv_circuitos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //rv_circuitos.setHasFixedSize(true);
        //rv_circuitos.setLayoutManager(new LinearLayoutManager(this));

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        reference = firestore.collection("Circuitos");
        mAuth = FirebaseAuth.getInstance();
        idCircuito = mAuth.getCurrentUser().getEmail();

        reference.whereEqualTo("id", idCircuito).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot listacircuitos : queryDocumentSnapshots){
                    //Circuitos circuitos = listacircuitos.toObject(Circuitos.class);
                    circuitosArrayList.add(listacircuitos.toObject(Circuitos.class));
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                circuitosAdapter = new CircuitosAdapter(circuitosArrayList, getApplicationContext());
                rv_circuitos.setAdapter(circuitosAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CircuitoActivity.this, "No se han podido cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });

        btn_nuevo_circuito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CircuitoActivity.this, CircuitosNuevoActivity.class));
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("Usuarios")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("role").equals("Organizador")) {
                        btn_nuevo_circuito.setVisibility(View.VISIBLE);

                    } if (documentSnapshot.getString("role").equals("Jefe de Equipo")) {
                        btn_nuevo_circuito.setVisibility(View.INVISIBLE);
                    }
                    if (documentSnapshot.getString("role").equals("Piloto")) {
                        btn_nuevo_circuito.setVisibility(View.INVISIBLE);
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
    }
}