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

    private RecyclerView rv_circuitos;
    private CircuitosAdapter circuitosAdapter;
    private ArrayList<Circuitos> listaCircuitos;

    private Button btn_nuevo_circuito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuito);

        btn_nuevo_circuito = findViewById(R.id.btn_nuevo_circuito);

        rv_circuitos = (RecyclerView) findViewById(R.id.rv_circuitos);
        rv_circuitos.setHasFixedSize(true);
        rv_circuitos.setLayoutManager(new LinearLayoutManager(this));

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        listaCircuitos = new ArrayList<Circuitos>();
        circuitosAdapter = new CircuitosAdapter(CircuitoActivity.this, listaCircuitos);

        rv_circuitos.setAdapter(circuitosAdapter);

        ListaCircuitos();

        btn_nuevo_circuito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CircuitoActivity.this, CircuitosNuevoActivity.class));
            }
        });



    }

    private void ListaCircuitos() {

        firestore.collection("Circuitos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error !=null){

                    Log.e("Firestore Error", error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){

                    if (dc.getType() == DocumentChange.Type.ADDED){

                        listaCircuitos.add(dc.getDocument().toObject(Circuitos.class));
                    }
                    circuitosAdapter.notifyDataSetChanged();
                }
            }
        });

    }


}