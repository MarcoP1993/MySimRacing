package com.example.mysimracing.RecyclerSanciones;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mysimracing.Clases.Sanciones;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ActivitySanciones extends AppCompatActivity {

    private Button btn_crearSancion;
    private ArrayList<Sanciones> sanciones;
    private RecyclerView rv_sanciones;
    private ListaSancionesAdapter adapterSanciones;
    FirebaseFirestore firestoredb;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanciones);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Buscando sanciones...");
        progressDialog.show();

        rv_sanciones = findViewById(R.id.rv_sanciones);
        rv_sanciones.setLayoutManager(new LinearLayoutManager(this));

        firestoredb = FirebaseFirestore.getInstance();

        sanciones = new ArrayList<Sanciones>();
        adapterSanciones = new ListaSancionesAdapter(ActivitySanciones.this, sanciones);

        rv_sanciones.setAdapter(adapterSanciones);

        ListaSanciones();


        btn_crearSancion = (Button) findViewById(R.id.btn_nueva_sancion);
        btn_crearSancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySanciones.this, ActivityNuevaSancion.class));
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
                        btn_crearSancion.setVisibility(View.VISIBLE);

                    } if (documentSnapshot.getString("role").equals("Jefe de Equipo")) {
                        btn_crearSancion.setVisibility(View.INVISIBLE);
                    }
                    if (documentSnapshot.getString("role").equals("Piloto")) {
                        btn_crearSancion.setVisibility(View.INVISIBLE);
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

    private void ListaSanciones(){
        firestoredb.collection("Sanciones")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        if(error !=null){

                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                sanciones.add(dc.getDocument().toObject(Sanciones.class));
                            }

                            adapterSanciones.notifyDataSetChanged();
                        }
                    }
                });
    }
}