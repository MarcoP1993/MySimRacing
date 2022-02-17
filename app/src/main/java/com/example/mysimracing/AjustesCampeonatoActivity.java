package com.example.mysimracing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AjustesCampeonatoActivity extends AppCompatActivity {

    private Button btn_actualizar;
    private Button btn_cambio_imagen;
    private EditText edt_nombre_campeonato;
    private ImageView actualizarImagenCampeonato;
    private ProgressDialog progressDialog;

    private static final int GALERIA_IMAGENES = 1;
    Uri imagen_seleccionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_campeonato);

        btn_actualizar = (Button) findViewById(R.id.btn_actualizar_datos);
        btn_cambio_imagen = (Button) findViewById(R.id.btn_cambiar_imagen);
        edt_nombre_campeonato = (EditText) findViewById(R.id.edt_ActulizarNomCamp);
        actualizarImagenCampeonato = (ImageView) findViewById(R.id.img_campeonato);
        progressDialog = new ProgressDialog(this);


        btn_cambio_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFoto(v);
            }
        });


    }

    public void cambiarFoto (View view){

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, GALERIA_IMAGENES);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERIA_IMAGENES && resultCode == Activity.RESULT_OK) {
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
    }

}