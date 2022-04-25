package com.example.mysimracing.RecicledListas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysimracing.R;

public class InscripcionesActivity extends AppCompatActivity {

    TextView enlace_web;
    Button btn_editar;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripciones);

        enlace_web = findViewById(R.id.txt_enlace);
        btn_editar = findViewById(R.id.btn_enlace_inscripcion);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pega o escribe el enlace web");

        //inflar custom_dialog
        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        EditText edt_enlace;
        edt_enlace = view.findViewById(R.id.enlaceweb);
        Button btn_crear = view.findViewById(R.id.btn_crear_enlace);
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlace_web.setText(edt_enlace.getText().toString());
                dialog.dismiss();
            }
        });
        builder.setView(view);

        dialog = builder.create();
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }
}