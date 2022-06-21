package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mysimracing.MenuOpciones.PerfilUsuarioActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ClasificacionActivity extends AppCompatActivity {

    private ImageView img_clasificacion;
    private Button btn_actualizar;

    private String idUsuario;
    private Uri imagenUrl;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoredb;
    StorageReference reference;
    FirebaseUser usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);

        img_clasificacion = findViewById(R.id.img_clasificacion);
        btn_actualizar = findViewById(R.id.btn_act_clasificacion);

        firebaseAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();
        reference = FirebaseStorage.getInstance().getReference();
        idUsuario = firebaseAuth.getCurrentUser().getUid();
        usuario = firebaseAuth.getCurrentUser();

        if(usuario !=null){
            if(usuario.getPhotoUrl() !=null){
                Picasso.get().load(usuario.getPhotoUrl()).into(img_clasificacion);
            }
        }

        DocumentReference docRef = firestoredb.collection("Clasificacion").document(idUsuario);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot , @Nullable FirebaseFirestoreException error) {

                StorageReference ref = reference.child("ImagenesClasificacion").child(idUsuario + ".jpg");
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(img_clasificacion);
                    }
                });
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarImagen(v);
            }
        });
    }


    public void actualizarImagen(View view) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imagenUrl = data.getData();
            img_clasificacion.setImageURI(imagenUrl);

            guardarImagenUsuario(imagenUrl);
        }

    }

    private void guardarImagenUsuario(Uri imagenUrl) {
        StorageReference ref = reference.child("ImagenesClasificacion").child(idUsuario + ".jpg");
        ref.putFile(imagenUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(img_clasificacion);
                    }
                });
                Toast.makeText(ClasificacionActivity.this, "Imagen Guardada", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ClasificacionActivity.this, "Error al subir la foto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("Usuarios")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("role").equals("Organizador")) {
                        btn_actualizar.setVisibility(View.VISIBLE);

                    } if (documentSnapshot.getString("role").equals("Jefe de Equipo")) {
                        btn_actualizar.setVisibility(View.INVISIBLE);
                    }
                    if (documentSnapshot.getString("role").equals("Piloto")) {
                        btn_actualizar.setVisibility(View.INVISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}