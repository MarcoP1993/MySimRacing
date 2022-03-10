package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.RecicledListas.ListaEventosAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizadorActivity extends AppCompatActivity {

    private Button btn_crearCampeonato;
    private ArrayList<Campeonatos> campeonatos = null;
    private RecyclerView rv_campeonatos = null;
    private CampeonatosAdapter adaptadorCampeonatos;
    private CircularImageView circularImageView;
    private TextView nombreOrg;
    String nombre;
    
    private FirebaseFirestore firestoredb;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizador);
        mAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();

        btn_crearCampeonato = findViewById(R.id.btn_nuevo_campeonato);
        circularImageView = findViewById(R.id.img_perfil_piloto);

        rv_campeonatos = (RecyclerView) findViewById(R.id.rv_campeonatos);
        rv_campeonatos.setLayoutManager(new LinearLayoutManager(this));
        campeonatos = new ArrayList<>();

        adaptadorCampeonatos = new CampeonatosAdapter(campeonatos);
        //adaptadorCampeonatos.notifyDataSetChanged();
        rv_campeonatos.setAdapter(adaptadorCampeonatos);

        nombreOrg = findViewById(R.id.txt_nom_organizador);
        Intent intent = getIntent();
        nombre = intent.getStringExtra("Nombre");
        nombreOrg.setText(nombre);

        firestoredb.collection("Campeonatos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot camp:list) {
                    Campeonatos obj=camp.toObject(Campeonatos.class);
                    campeonatos.add(obj);
                }
                adaptadorCampeonatos.notifyDataSetChanged();
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


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.perfil_menu){
            startActivity(new Intent(OrganizadorActivity.this, PerfilUsuarioActivity.class));

        }else if (id == R.id.info_menu){

        }else if (id == R.id.cerrar_sesion){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(OrganizadorActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}