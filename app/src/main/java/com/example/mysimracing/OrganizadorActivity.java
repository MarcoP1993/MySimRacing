package com.example.mysimracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrganizadorActivity extends AppCompatActivity {

    private Button btn_crearCampeonato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizador);
        btn_crearCampeonato = findViewById(R.id.btn_nuevo_campeonato);

        btn_crearCampeonato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrganizadorActivity.this, ActivityNuevoCampeonato.class));
            }
        });
    }


}