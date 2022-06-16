package com.example.mysimracing.RecyclerCampeonatos;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mysimracing.Authentication.LoginActivity;
import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AjustesCampeonatoActivity extends AppCompatActivity {

    private Button btn_actualizar, btn_cambio_imagen;
    private EditText edt_nombre_campeonato;
    private CircularImageView actualizarImagenCampeonato;
    private ProgressDialog progressDialog;
    private StorageReference storageRef;
    private FirebaseAuth firebaseAuth;
    private String id;
    private String idUsuario;
    private Campeonatos campeonatos;

    DocumentSnapshot documentSnapshot;

    private FirebaseFirestore firestoredb = FirebaseFirestore.getInstance();

    private static final int GALERIA_IMAGENES = 1;
    Uri imagen_seleccionada = null;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser !=null){
            currentUser.reload();
        }else{
            Toast.makeText(AjustesCampeonatoActivity.this, "Debes registrarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            //actualizarUI(user);
            Intent intent = new Intent(AjustesCampeonatoActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_campeonato);

        btn_actualizar = (Button) findViewById(R.id.btn_actualizar_datos);
        btn_cambio_imagen = (Button) findViewById(R.id.btn_cambiar_imagen);

        edt_nombre_campeonato = (EditText) findViewById(R.id.edt_ActulizarNomCamp);
        actualizarImagenCampeonato = (CircularImageView) findViewById(R.id.img_campeonato);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(AjustesCampeonatoActivity.this);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Espere...");

        idUsuario = firebaseAuth.getCurrentUser().getEmail();


        btn_cambio_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFoto(v);
            }

        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_nombre_campeonato.getText().length()>0){
                    actualizarDatosCampeonato(edt_nombre_campeonato.getText().toString());
                }else{
                    edt_nombre_campeonato.setError("Rellena el campo");
                    Toast.makeText(AjustesCampeonatoActivity.this, "Por favor, completa todos los campos!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Intent intent = getIntent();
        if(intent !=null){
            id = intent.getStringExtra("id");
            edt_nombre_campeonato.setText(intent.getStringExtra("nombreCampeonato"));
            Glide.with(getApplicationContext()).load(intent.getStringExtra("Imagen")).into(actualizarImagenCampeonato);
        }


    }

    public void cambiarFoto (View view){

        final CharSequence[] items = {"Hacer Foto", "Elegir del archivo", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AjustesCampeonatoActivity.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
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
                startActivityForResult(chooserIntent, GALERIA_IMAGENES);
            }else if(items[item].equals("Cancelar")){
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERIA_IMAGENES && resultCode == RESULT_OK && data !=null) {
            imagen_seleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_seleccionada);
                actualizarImagenCampeonato.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(requestCode == 10 && resultCode == RESULT_OK){
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() ->{
                Bitmap bitmap = (Bitmap) extras.get("data");
                actualizarImagenCampeonato.post(()->{
                    actualizarImagenCampeonato.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }

    public void actualizarDatosCampeonato(String nombreCampeonato){
        progressDialog.show();
        actualizarImagenCampeonato.setDrawingCacheEnabled(true);
        actualizarImagenCampeonato.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) actualizarImagenCampeonato.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //Subir imagen
        FirebaseStorage fstorage = FirebaseStorage.getInstance();
        storageRef = fstorage.getReference("campeonato").child("imagen" + Timestamp.now().toDate().getTime() + ".jpg");
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AjustesCampeonatoActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getMetadata() != null){
                    if(taskSnapshot.getMetadata().getReference().getDownloadUrl() !=null){
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.getResult() !=null){
                                    guardarDatos(nombreCampeonato, task.getResult().toString());
                                }else{
                                    Toast.makeText(AjustesCampeonatoActivity.this, "Actualizado incorrecto", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }
                        });
                    }else{
                        Toast.makeText(AjustesCampeonatoActivity.this, "Actualizado incorrecto", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(AjustesCampeonatoActivity.this, "Actualizado incorrecto", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });


    }

    private void guardarDatos(String nombreCampeonato, String imagen) {

        Campeonatos c = new Campeonatos();
        c.setNombreCampeonato(nombreCampeonato);
        c.setImagen(imagen);
        /*c.setPlataforma(c.getPlataforma());
        c.setJuego(c.getJuego());
        c.setRango_fechas(c.getRango_fechas());
        c.setTipoCampeonato(c.getTipoCampeonato());*/


        if(idUsuario !=null){
            firestoredb.collection("Campeonatos").document(idUsuario).set(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AjustesCampeonatoActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(AjustesCampeonatoActivity.this, "No se ha podido actualizar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        }


    }







