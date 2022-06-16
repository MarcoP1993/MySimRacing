package com.example.mysimracing.Roles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.MenuOpciones.InfoAppActivity;
import com.example.mysimracing.Authentication.LoginActivity;
import com.example.mysimracing.MenuOpciones.PerfilUsuarioActivity;
import com.example.mysimracing.R;
import com.example.mysimracing.RecyclerCampeonatos.CampeonatosAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityPiloto extends AppCompatActivity {

    private CircularImageView circularImageView;
    private String idpiloto;
    private TextView txt_nom_piloto, txt_nick_piloto;
    private RecyclerView rv_campeonato_piloto;
    private CampeonatosAdapter campeonatosAdapter;
    private ArrayList<Campeonatos> campeonatospiloto = null;


    SwipeRefreshLayout swipeActualizar;

    private FirebaseFirestore firestoredb;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piloto);
        circularImageView = findViewById(R.id.img_perfil_piloto);
        txt_nom_piloto = findViewById(R.id.txt_nom_piloto);
        txt_nick_piloto = findViewById(R.id.txt_nick_piloto);

        mAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();
        idpiloto = mAuth.getCurrentUser().getUid();

        datosUsuario();

        rv_campeonato_piloto = (RecyclerView) findViewById(R.id.rv_piloto_campeonato);
        rv_campeonato_piloto.setLayoutManager(new LinearLayoutManager(this));
        campeonatospiloto = new ArrayList<>();

        campeonatosAdapter = new CampeonatosAdapter(campeonatospiloto);
        rv_campeonato_piloto.setAdapter(campeonatosAdapter);

        swipeActualizar = findViewById(R.id.swipecamppiloto);
        swipeActualizar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                campeonatospiloto.clear();
                firestoredb.collection("Campeonatos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot camp:list) {
                            Campeonatos obj=camp.toObject(Campeonatos.class);
                            campeonatospiloto.add(obj);
                        }
                        campeonatosAdapter.notifyDataSetChanged();
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
                    campeonatospiloto.add(obj);

                }
                campeonatosAdapter.notifyDataSetChanged();
                //swipeActualizar.setRefreshing(false);

            }
        });


    }

    private void datosUsuario() {
        DocumentReference docRef = firestoredb.collection("Usuarios").document(idpiloto);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot , @Nullable FirebaseFirestoreException error) {
                txt_nom_piloto.setText("Nombre: " + documentSnapshot.getString("nombre"));
                txt_nick_piloto.setText("Nick: " + documentSnapshot.getString("nickname"));
                Picasso.get().load(documentSnapshot.getString("fotoUsuario")).into(circularImageView);

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
            startActivity(new Intent(ActivityPiloto.this, PerfilUsuarioActivity.class));

        }else if (id == R.id.info_menu){
            startActivity(new Intent(ActivityPiloto.this, InfoAppActivity.class));

        }else if (id == R.id.cerrar_sesion){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ActivityPiloto.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}