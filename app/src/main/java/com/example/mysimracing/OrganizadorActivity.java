package com.example.mysimracing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.RecicledListas.ListaEventosAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class OrganizadorActivity extends AppCompatActivity {

    private Button btn_crearCampeonato;
    private ArrayList<Campeonatos> campeonatos = null;
    private RecyclerView rv_campeonatos = null;
    private ListaEventosAdapter adaptadorCampeonatos;
    private CircularImageView circularImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizador);
        btn_crearCampeonato = findViewById(R.id.btn_nuevo_campeonato);
        circularImageView = findViewById(R.id.img_perfil_piloto);

        rv_campeonatos = (RecyclerView) findViewById(R.id.rv_campeonatos);
        campeonatos = new ArrayList<Campeonatos>();

        campeonatos.add(new Campeonatos("Torneo 1", "12feb - 04May", "GT Sport", "PS4", "individual"));
        campeonatos.add(new Campeonatos("Torneo 2", "12feb - 04May", "Forza", "XBOX", "individual"));
        campeonatos.add(new Campeonatos("Torneo 3", "12feb - 04May", "F1 2021", "PS5", "equipos"));
        campeonatos.add(new Campeonatos("Torneo 4", "12feb - 04May", "iRacing", "PC", "equipos"));

        adaptadorCampeonatos = new ListaEventosAdapter(this,campeonatos);
        rv_campeonatos.setAdapter(adaptadorCampeonatos);
        rv_campeonatos.setLayoutManager(new LinearLayoutManager(this));

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

        }else if (id == R.id.info_menu){

        }else if (id == R.id.cerrar_sesion){

        }

        return super.onOptionsItemSelected(item);
    }

}