package com.example.mysimracing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.RecicledListas.CampeonatosAdapter;
import com.example.mysimracing.RecyclerEquipos.ActivityEquipos;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class ActivityJefeEquipo extends AppCompatActivity{

    private Button btn_equipos;
    private ArrayList<Campeonatos> campeonatos = new ArrayList<>();
    private RecyclerView rv_campeonatos = null;
    private CampeonatosAdapter adaptadorCampeonatos;
    private CircularImageView circularImageView;
    private TextView nombreJefe;
    String idnombre;

    SwipeRefreshLayout swipeActualizar;

    private FirebaseFirestore firestoredb;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jefe_equipo);
        circularImageView = findViewById(R.id.img_perfil_jefe);
        nombreJefe = findViewById(R.id.txt_nombreEquipo);

        mAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();
        idnombre = mAuth.getCurrentUser().getUid();

        datosusuario();

        btn_equipos = findViewById(R.id.btn_equipos);

        rv_campeonatos = (RecyclerView) findViewById(R.id.rv_campeonatos_jefe);
        rv_campeonatos.setLayoutManager(new LinearLayoutManager(this));

        adaptadorCampeonatos = new CampeonatosAdapter(campeonatos);
        rv_campeonatos.setAdapter(adaptadorCampeonatos);


        swipeActualizar = findViewById(R.id.swipeJefe);
        swipeActualizar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                campeonatos.clear();
                firestoredb.collection("Campeonatos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot camp:list) {
                            Campeonatos obj=camp.toObject(Campeonatos.class);
                            obj.getId();
                            campeonatos.add(obj);

                        }
                        adaptadorCampeonatos.notifyDataSetChanged();
                        swipeActualizar.setRefreshing(false);

                    }
                });
            }
        });

        firestoredb.collection("Campeonatos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot camp:list) {
                    Campeonatos obj=camp.toObject(Campeonatos.class);
                    obj.getId();
                    campeonatos.add(obj);
                }
                adaptadorCampeonatos.notifyDataSetChanged();
                swipeActualizar.setRefreshing(false);

            }
        });


        btn_equipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityJefeEquipo.this, ActivityEquipos.class));
            }
        });
    }

    private void datosusuario() {
        DocumentReference docRef = firestoredb.collection("Usuarios").document(idnombre);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot , @Nullable FirebaseFirestoreException error) {
                nombreJefe.setText("Nombre: " + documentSnapshot.getString("nombre"));

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.perfil_menu){
            startActivity(new Intent(ActivityJefeEquipo.this, PerfilUsuarioActivity.class));

        }else if (id == R.id.info_menu){
            startActivity(new Intent(ActivityJefeEquipo.this, InfoAppActivity.class));

        }else if (id == R.id.cerrar_sesion){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ActivityJefeEquipo.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



}