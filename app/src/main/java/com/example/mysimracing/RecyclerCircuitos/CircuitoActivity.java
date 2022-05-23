package com.example.mysimracing.RecyclerCircuitos;

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

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.Clases.Circuitos;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class CircuitoActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseStorage storage;

    RecyclerView rv_circuitos;
    CircuitosAdapter circuitosAdapter;
    ArrayList<Circuitos> listaCircuitos;

    Button btn_nuevo_circuito;

    SwipeRefreshLayout swipeCircuitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuito);

        btn_nuevo_circuito = findViewById(R.id.btn_nuevo_circuito);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        rv_circuitos = (RecyclerView) findViewById(R.id.rv_circuitos);
        rv_circuitos.setLayoutManager(new LinearLayoutManager(this));

        listaCircuitos = new ArrayList<>();
        circuitosAdapter = new CircuitosAdapter(CircuitoActivity.this, listaCircuitos);

        rv_circuitos.setAdapter(circuitosAdapter);

        btn_nuevo_circuito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CircuitoActivity.this, CircuitosNuevoActivity.class));
            }
        });

        swipeCircuitos = findViewById(R.id.swipe_circuitos);
        swipeCircuitos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listaCircuitos.clear();
                firestore.collection("Circuitos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot camp:list) {
                            Circuitos obj=camp.toObject(Circuitos.class);
                            listaCircuitos.add(obj);
                        }
                        circuitosAdapter.notifyDataSetChanged();
                        swipeCircuitos.setRefreshing(false);

                    }
                });
            }
        });

        firestore.collection("Circuitos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot camp:list) {
                    Circuitos obj=camp.toObject(Circuitos.class);
                    listaCircuitos.add(obj);
                }
                circuitosAdapter.notifyDataSetChanged();
                //swipeCircuitos.setRefreshing(false);

            }
        });

    }


}