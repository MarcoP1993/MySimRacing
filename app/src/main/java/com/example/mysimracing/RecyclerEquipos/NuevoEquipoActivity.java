package com.example.mysimracing.RecyclerEquipos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mysimracing.Clases.Circuitos;
import com.example.mysimracing.Clases.Equipos;
import com.example.mysimracing.R;
import com.example.mysimracing.RecyclerCircuitos.CircuitoActivity;
import com.example.mysimracing.RecyclerCircuitos.CircuitosNuevoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class NuevoEquipoActivity extends AppCompatActivity {

    private EditText edt_nombre, edt_pais, edt_jefe, edt_web, edt_discord;
    private ImageView img_logo;
    private Button btn_crear_equipo, btn_elegir_logo;

    private FirebaseFirestore firestoredb;
    StorageReference storagedb;
    private FirebaseAuth firebaseUser;

    private ProgressDialog progressDialog;

    Equipos equipos;

    String id;
    String idUser;

    private static final int GALLERY_CODE = 1;
    Uri imagenUrl = null;

    //Valores a guardar

    private String nombreEquipo;
    private String paisEquipo;
    private String jefeEquipo;
    private String webEquipo;
    private String discordEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_equipo);

        firestoredb = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance();


        edt_nombre = findViewById(R.id.edt_nombre_equipo);
        edt_pais = findViewById(R.id.edt_pais_equipo);
        edt_jefe = findViewById(R.id.edt_nombre_jefe);
        edt_web = findViewById(R.id.edt_web_equipo);
        edt_discord = findViewById(R.id.edt_discord_equipo);
        img_logo = findViewById(R.id.img_logo_equipo);
        btn_crear_equipo = findViewById(R.id.btn_crear_equipo);
        btn_elegir_logo = findViewById(R.id.btn_elegir_logo);

        progressDialog = new ProgressDialog(NuevoEquipoActivity.this);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Espere...");


        btn_elegir_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarLogo(v);
            }
        });

        Intent intent = getIntent();
        if(intent !=null){
            id = intent.getStringExtra("id");
            edt_nombre.setText(intent.getStringExtra("nombreEquipo"));
            Glide.with(getApplicationContext()).load(intent.getStringExtra("Imagen")).into(img_logo);
        }

        btn_crear_equipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEquipo();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data !=null) {
            imagenUrl = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagenUrl);
                img_logo.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(requestCode == 10 && resultCode == RESULT_OK){
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() ->{
                Bitmap bitmap = (Bitmap) extras.get("data");
                img_logo.post(()->{
                    img_logo.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }

    private void guardarEquipo() {
        progressDialog.show();
        img_logo.setDrawingCacheEnabled(true);
        img_logo.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img_logo.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //Subir imagen
        FirebaseStorage fstorage = FirebaseStorage.getInstance();
        storagedb = fstorage.getReference("circuitos").child("imagen" + Timestamp.now().toDate().getTime() + ".jpg");
        UploadTask uploadTask = storagedb.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getMetadata() !=null){
                    if(taskSnapshot.getMetadata().getReference().getDownloadUrl() !=null){
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.getResult() !=null){
                                    //aqui registro los datos
                                    idUser = firebaseUser.getCurrentUser().getEmail();
                                    nombreEquipo = edt_nombre.getText().toString();
                                    paisEquipo = edt_pais.getText().toString();
                                    jefeEquipo = edt_jefe.getText().toString();
                                    webEquipo = edt_web.getText().toString();
                                    discordEquipo = edt_discord.getText().toString();

                                    equipos = new Equipos(idUser, nombreEquipo, paisEquipo, jefeEquipo , webEquipo, discordEquipo , task.getResult().toString());

                                    firestoredb.collection("Equipos").add(equipos).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            Toast.makeText(NuevoEquipoActivity.this, "Circuito creado correctamente", Toast.LENGTH_SHORT).show();

                                        }


                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(NuevoEquipoActivity.this, "Error al crear el circuito", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                    progressDialog.dismiss();
                                }else{
                                    Toast.makeText(NuevoEquipoActivity.this, "Creado incorrecto", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(NuevoEquipoActivity.this, "Creado incorrecto", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(NuevoEquipoActivity.this, "Creado incorrecto", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NuevoEquipoActivity.this, "Error al crear", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void cambiarLogo(View v) {
        final CharSequence[] items = {"Hacer Foto", "Elegir del archivo", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NuevoEquipoActivity.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.drawable.ic_baseline_sports_motorsports_24);
        builder.setItems(items, (dialog, item) -> {
            if(items[item].equals("Hacer Foto")){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,10);
            }else if(items[item].equals("Elegir del archivo")){
                /*Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"selecciona imagen"),20);*/
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent(), "Selecciona una imagen");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                startActivityForResult(chooserIntent, GALLERY_CODE);
            }else if(items[item].equals("Cancelar")){
                dialog.dismiss();
            }
        });
        builder.show();
    }



    /*btn_crear_equipo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nombreEquipo = edt_nombre.getText().toString();
            paisEquipo = edt_pais.getText().toString();
            jefeEquipo = edt_jefe.getText().toString();
            webEquipo = edt_web.getText().toString();
            discordEquipo = edt_discord.getText().toString();

            if(!(nombreEquipo.isEmpty() && paisEquipo.isEmpty() && jefeEquipo.isEmpty() && webEquipo.isEmpty() && discordEquipo.isEmpty() && imagenUrl!=null)){
                StorageReference filepath = storagedb.getReference().child("LogoEquipos").child(imagenUrl.getLastPathSegment());
                filepath.putFile(imagenUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> descargarURL = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String t = task.getResult().toString();

                                //crear coleccion en firestore
                                equipos = new Equipos(nombreEquipo, paisEquipo, jefeEquipo, webEquipo, discordEquipo, imagenUrl.toString());

                                firestoredb.collection("Equipos").add(equipos).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(NuevoEquipoActivity.this, "Equipo creado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(NuevoEquipoActivity.this, ActivityEquipos.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(NuevoEquipoActivity.this, "No se pudo crear al equipo", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                });

            }else {
                Toast.makeText(NuevoEquipoActivity.this, "Completa todo los campos", Toast.LENGTH_SHORT).show();
            }
        }
    });*/


}