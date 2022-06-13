package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.RecicledListas.CampeonatosAdapter;
import com.example.mysimracing.RecyclerEquipos.ActivityEquipos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class OrganizadorActivity extends AppCompatActivity {

    private Button btn_crearCampeonato;
    private ArrayList<Campeonatos> campeonatos = new ArrayList<>();
    private RecyclerView rv_campeonatos = null;
    private CampeonatosAdapter adaptadorCampeonatos;
    private CircularImageView circularImageView;
    private TextView nombreOrg;
    String nombrecampeonato;
    String nombreUser;

    SwipeRefreshLayout swipeActualizar;
    
    private FirebaseFirestore firestoredb;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizador);
        mAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();
        nombrecampeonato = mAuth.getCurrentUser().getEmail();
        nombreUser = mAuth.getCurrentUser().getUid();

        btn_crearCampeonato = findViewById(R.id.btn_nuevo_campeonato);
        circularImageView = findViewById(R.id.img_perfil_piloto);

        rv_campeonatos = (RecyclerView) findViewById(R.id.rv_campeonatos);
        rv_campeonatos.setLayoutManager(new LinearLayoutManager(this));

        datosUsuario();

        adaptadorCampeonatos = new CampeonatosAdapter(campeonatos);
        //adaptadorCampeonatos.notifyDataSetChanged();
        rv_campeonatos.setAdapter(adaptadorCampeonatos);

        nombreOrg = findViewById(R.id.txt_nom_organizador);



        swipeActualizar = findViewById(R.id.swipeActualizar);
        swipeActualizar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                campeonatos.clear();
                firestoredb.collection("Campeonatos").whereEqualTo("id", nombrecampeonato).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

        firestoredb.collection("Campeonatos").whereEqualTo("id", nombrecampeonato).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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



        //Query consulta = firestoredb.collection("Campeonatos");

        /*FirestoreRecyclerOptions<Campeonatos> campeonatosFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Campeonatos>()
                .setQuery(consulta, Campeonatos.class).build();*/

        btn_crearCampeonato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrganizadorActivity.this, ActivityNuevoCampeonato.class));
            }
        });
    }

    private void datosUsuario() {
            DocumentReference docRef = firestoredb.collection("Usuarios").document(nombreUser);
            docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot , @Nullable FirebaseFirestoreException error) {
                    nombreOrg.setText("Nombre: " + documentSnapshot.getString("nombre"));

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
            startActivity(new Intent(OrganizadorActivity.this, PerfilUsuarioActivity.class));

        }else if (id == R.id.info_menu){
            startActivity(new Intent(OrganizadorActivity.this, InfoAppActivity.class));

        }else if (id == R.id.cerrar_sesion){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(OrganizadorActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}