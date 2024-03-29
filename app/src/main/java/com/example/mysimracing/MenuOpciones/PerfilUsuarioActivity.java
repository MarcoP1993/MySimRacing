package com.example.mysimracing.MenuOpciones;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysimracing.Authentication.LoginActivity;
import com.example.mysimracing.Clases.Usuarios;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 100;
    private EditText edt_nombre_perfil;
    private EditText edt_nick_perfil;
    private TextView nombre_perfil, nick_perfil;
    private CircularImageView imagenUsuario;
    private RadioGroup rg_roles_perfil = null;
    private RadioButton rb_organizador_perfil_act;
    private RadioButton rb_jefe_equipo_perfil_act;
    private RadioButton rb_piloto_perfil_act;

    private Button actualizar_perfil, btn_eliminar_usuario;

    private String idUsuario;
    private Uri imagenUrl;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoredb;
    StorageReference reference;
    FirebaseUser usuario;


    private String nombre = "";
    private String nickname = "";
    private String correo = "";
    private String contrasena = "";
    private String role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        edt_nombre_perfil = (EditText) findViewById(R.id.edt_nombrePerfil);
        edt_nick_perfil = (EditText) findViewById(R.id.edt_NicknamePerfil);
        rg_roles_perfil = (RadioGroup) findViewById(R.id.rg_roles_perfil);
        rb_organizador_perfil_act = (RadioButton) findViewById(R.id.rb_orga_perfil);
        rb_jefe_equipo_perfil_act = (RadioButton) findViewById(R.id.rb_jefe_perfil);
        rb_piloto_perfil_act = (RadioButton) findViewById(R.id.rb_pilo_perfil);
        actualizar_perfil = (Button) findViewById(R.id.btn_act_perfil);
        nombre_perfil = (TextView) findViewById(R.id.txt_nombre_perfil);
        nick_perfil = (TextView) findViewById(R.id.txt_nick_perfil);
        imagenUsuario = (CircularImageView) findViewById(R.id.img_perfil);
        btn_eliminar_usuario = (Button) findViewById(R.id.btn_borrar_perfil);

        firebaseAuth = FirebaseAuth.getInstance();
        usuario = firebaseAuth.getCurrentUser();
        firestoredb = FirebaseFirestore.getInstance();
        reference = FirebaseStorage.getInstance().getReference();
        idUsuario = firebaseAuth.getCurrentUser().getUid();



        if(usuario !=null){
            if(usuario.getPhotoUrl() !=null){
                Picasso.get().load(usuario.getPhotoUrl()).into(imagenUsuario);
            }
        }

        DocumentReference docRef = firestoredb.collection("Usuarios").document(idUsuario);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot , @Nullable FirebaseFirestoreException error) {
                nombre_perfil.setText("Nombre: " + documentSnapshot.getString("nombre"));
                nick_perfil.setText("Nick: " + documentSnapshot.getString("nickname"));
                StorageReference ref = reference.child("ImagenesPerfil").child(idUsuario + ".jpg");
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imagenUsuario);
                    }
                });
            }
        });

        //boton actualizar
        actualizar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });

        //boton eliminar cuenta
        btn_eliminar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PerfilUsuarioActivity.this);
                builder.setMessage("¿Estas seguro de que quieres eliminar tu cuenta?\n " +
                                    "Are you sure you want to delete your account?")
                        .setTitle("Eliminar Cuenta");

                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        firestoredb.collection("Usuarios").document(idUsuario).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        usuario.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            Toast.makeText(PerfilUsuarioActivity.this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        Toast.makeText(PerfilUsuarioActivity.this, "Perfil Eliminado", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(PerfilUsuarioActivity.this, LoginActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PerfilUsuarioActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void actualizarDatos() {
        nombre = edt_nombre_perfil.getText().toString();
        nickname = edt_nick_perfil.getText().toString();
        role = "";
        if(!(nombre.isEmpty() && nickname.isEmpty())){

            if(!(rb_organizador_perfil_act.isChecked() || rb_jefe_equipo_perfil_act.isChecked() || rb_piloto_perfil_act.isChecked())){
                Toast.makeText(PerfilUsuarioActivity.this, "Selecciona un rol", Toast.LENGTH_SHORT).show();
                return;
            }else {
                elegirRole();
                guardardatos();
            }
        }else {
            Toast.makeText(PerfilUsuarioActivity.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public void elegirfoto(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imagenUrl = data.getData();
            imagenUsuario.setImageURI(imagenUrl);

            guardarImagenUsuario(imagenUrl);
        }

    }

    private void guardarImagenUsuario(Uri uri){
        StorageReference ref = reference.child("ImagenesPerfil").child(idUsuario + ".jpg");
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(imagenUsuario);
                    }
                });
                Toast.makeText(PerfilUsuarioActivity.this, "Imagen Guardada", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PerfilUsuarioActivity.this, "Error al subir la foto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void elegirRole (){
        int seleccionadoOk = rg_roles_perfil.getCheckedRadioButtonId();

        if(seleccionadoOk == R.id.rb_orga_perfil){

            role = "Organizador";
            Toast.makeText(this,"Has seleccionado Organizador",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            String nombre = nombre_perfil.getText().toString();
            intent.putExtra("Nombre", nombre);
            startActivity(intent);

        }else if(seleccionadoOk == R.id.rb_jefe_perfil){

            role = "Jefe de Equipo";
            Toast.makeText(this,"Has seleccionado Jefe de equipo",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            String nombre = nombre_perfil.getText().toString();
            intent.putExtra("Nombre", nombre);
            startActivity(intent);

        }else if (seleccionadoOk == R.id.rb_pilo_perfil){

            role = "Piloto";
            Toast.makeText(this,"Has seleccionado Piloto",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            String nombre = nombre_perfil.getText().toString();
            String nick = nick_perfil.getText().toString();
            intent.putExtra("Nombre", nombre);
            intent.putExtra("nickname", nick);
            startActivity(intent);

        }else {
            Toast.makeText(PerfilUsuarioActivity.this, "Selecciona un rol", Toast.LENGTH_SHORT).show();

        }

    }

    public void guardardatos(){

        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre);
        user.put("nickname", nickname);
        user.put("role", role);
        user.put("fotoUsuario", imagenUrl);

        firestoredb.collection("Usuarios").document(idUsuario).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(PerfilUsuarioActivity.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
            }
        });
    }

}