package com.example.mysimracing.RecyclerCircuitos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mysimracing.Clases.Circuitos;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CircuitosNuevoActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    FirebaseStorage storage;
    EditText nombre, pais, dia, hora, categoria;
    ImageButton img_circuito;
    Button btn_insertar_circuito;
    ProgressDialog progressDialog;

    private static final int Gallery_code = 1;
    Uri imagenUrl = null;

    //valores a guardar
    private String nombreCircuito;
    private String paisCircuito;
    private String diaCarrera;
    private String horaCarrera;
    private String categoriaCoche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuitos_nuevo);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        nombre = findViewById(R.id.edt_nombre_circuito);
        pais = findViewById(R.id.edt_pais_circuito);
        dia = findViewById(R.id.edt_dia_carrera);
        hora = findViewById(R.id.edt_hora_carrera);
        categoria = findViewById(R.id.edt_cat_coche);
        img_circuito = findViewById(R.id.img_circuito);
        btn_insertar_circuito = findViewById(R.id.btn_crear_circuito);


        img_circuito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_code && resultCode == RESULT_OK){
            imagenUrl=data.getData();
            img_circuito.setImageURI(imagenUrl);
        }

    btn_insertar_circuito.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nombreCircuito = nombre.getText().toString();
            paisCircuito = pais.getText().toString();
            diaCarrera = dia.getText().toString();
            horaCarrera = hora.getText().toString();
            categoriaCoche = categoria.getText().toString();

            if(!(nombreCircuito.isEmpty() && paisCircuito.isEmpty() && diaCarrera.isEmpty() && horaCarrera.isEmpty() && categoriaCoche.isEmpty() && imagenUrl!=null)){

                StorageReference filepath = storage.getReference().child("ImagenCircuito").child(imagenUrl.getLastPathSegment());
                filepath.putFile(imagenUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> descargarURL= taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String t = task.getResult().toString();

                                //crear coleccion en firestore

                                Circuitos circuitos = new Circuitos(nombreCircuito, paisCircuito, categoriaCoche , horaCarrera, diaCarrera , imagenUrl.toString());

                                firestore.collection("Circuitos").add(circuitos).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        Toast.makeText(CircuitosNuevoActivity.this, "Circuito creado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CircuitosNuevoActivity.this, CircuitoActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CircuitosNuevoActivity.this, "Error al crear el circuito", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        });
                    }
                });

            }else {

                Toast.makeText(CircuitosNuevoActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        }
    });
    }
}