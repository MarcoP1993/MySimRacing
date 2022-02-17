package com.example.mysimracing.RecyclerSanciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mysimracing.ActivityNuevaSancion;
import com.example.mysimracing.Clases.Sanciones;
import com.example.mysimracing.R;

import java.util.ArrayList;

public class ActivitySanciones extends AppCompatActivity {

    private Button btn_crearSancion;
    private ArrayList<Sanciones> sanciones;
    private RecyclerView rv_sanciones;
    private ListaSancionesAdapter adapterSanciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanciones);
        btn_crearSancion = (Button) findViewById(R.id.btn_nueva_sancion);

        btn_crearSancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySanciones.this, ActivityNuevaSancion.class));
            }
        });
    }
}