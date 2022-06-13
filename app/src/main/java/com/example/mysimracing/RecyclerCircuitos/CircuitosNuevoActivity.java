package com.example.mysimracing.RecyclerCircuitos;

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
import com.example.mysimracing.AjustesCampeonatoActivity;
import com.example.mysimracing.Clases.Circuitos;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CircuitosNuevoActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    StorageReference storage;
    private FirebaseAuth firebaseUser;

    EditText nombre, pais, dia, hora, categoria;
    ImageView img_circuito;
    Button btn_insertar_circuito, btn_elegir_foto;
    String id;

    private ProgressDialog progressDialog;

    Circuitos circuitos;

    private static final int GALLERY_CODE = 1;
    Uri imagenUrl = null;

    //valores a guardar
    private String iduser;
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
        firebaseUser = FirebaseAuth.getInstance();

        nombre = findViewById(R.id.edt_nombre_circuito);
        pais = findViewById(R.id.edt_pais_circuito);
        dia = findViewById(R.id.edt_dia_carrera);
        hora = findViewById(R.id.edt_hora_carrera);
        categoria = findViewById(R.id.edt_cat_coche);
        img_circuito = findViewById(R.id.img_foto_circuito);
        btn_insertar_circuito = findViewById(R.id.btn_crear_circuito);
        btn_elegir_foto = findViewById(R.id.btn_cambiar_foto);

        progressDialog = new ProgressDialog(CircuitosNuevoActivity.this);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Espere...");


        btn_elegir_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFoto(v);
            }
        });

        Intent intent = getIntent();
        if(intent !=null){
            id = intent.getStringExtra("id");
            nombre.setText(intent.getStringExtra("nombreCampeonato"));
            Glide.with(getApplicationContext()).load(intent.getStringExtra("Imagen")).into(img_circuito);
        }

        btn_insertar_circuito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCircuito();
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
                img_circuito.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(requestCode == 10 && resultCode == RESULT_OK){
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() ->{
                Bitmap bitmap = (Bitmap) extras.get("data");
                img_circuito.post(()->{
                    img_circuito.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }

    public void cambiarFoto (View view){

        final CharSequence[] items = {"Hacer Foto", "Elegir del archivo", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CircuitosNuevoActivity.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.drawable.ic_baseline_outlined_flag_24);
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

    public void guardarCircuito(){
        progressDialog.show();
        img_circuito.setDrawingCacheEnabled(true);
        img_circuito.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img_circuito.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //Subir imagen
        FirebaseStorage fstorage = FirebaseStorage.getInstance();
        storage = fstorage.getReference("circuitos").child("imagen" + Timestamp.now().toDate().getTime() + ".jpg");
        UploadTask uploadTask = storage.putBytes(data);
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
                                    iduser = firebaseUser.getCurrentUser().getEmail();
                                    nombreCircuito = nombre.getText().toString();
                                    paisCircuito = pais.getText().toString();
                                    diaCarrera = dia.getText().toString();
                                    horaCarrera = hora.getText().toString();
                                    categoriaCoche = categoria.getText().toString();

                                    circuitos = new Circuitos(iduser, nombreCircuito, paisCircuito, categoriaCoche , horaCarrera, diaCarrera , task.getResult().toString());

                                    firestore.collection("Circuitos").add(circuitos).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            Toast.makeText(CircuitosNuevoActivity.this, "Circuito creado correctamente", Toast.LENGTH_SHORT).show();

                                        }


                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CircuitosNuevoActivity.this, "Error al crear el circuito", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                    progressDialog.dismiss();
                                }else{
                                    Toast.makeText(CircuitosNuevoActivity.this, "Creado incorrecto", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(CircuitosNuevoActivity.this, "Creado incorrecto", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(CircuitosNuevoActivity.this, "Creado incorrecto", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CircuitosNuevoActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    /*btn_insertar_circuito.setOnClickListener(new View.OnClickListener() {
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

                                circuitos = new Circuitos(nombreCircuito, paisCircuito, categoriaCoche , horaCarrera, diaCarrera , imagenUrl.toString());

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
    });*/

}