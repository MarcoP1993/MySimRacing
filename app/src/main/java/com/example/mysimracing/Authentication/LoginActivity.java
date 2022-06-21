package com.example.mysimracing.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysimracing.R;
import com.example.mysimracing.Roles.ActivityJefeEquipo;
import com.example.mysimracing.Roles.ActivityPiloto;
import com.example.mysimracing.Roles.OrganizadorActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_correo_inicio = null;
    private EditText edt_password_inicio = null;
    private Button btn_acceder;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoredb;

    //variables con los datos para inicar sesion
    private String email = "";
    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_correo_inicio = (EditText) findViewById(R.id.correo_inicio);
        edt_password_inicio = (EditText) findViewById(R.id.password_inicio);
        btn_acceder = (Button) findViewById(R.id.boton_acceso);

        firebaseAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();

        btn_acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_correo_inicio.getText().toString();
                password = edt_password_inicio.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    acceso();

                }else {
                    Toast.makeText(LoginActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void registro(View view) {
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public void acceso() {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
               @Override
               public void onSuccess(AuthResult authResult) {
                   Toast.makeText(LoginActivity.this, "Login correcto, bienvenid@", Toast.LENGTH_SHORT).show();
                   comprobarRole(authResult.getUser().getUid());
                   finish();
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(LoginActivity.this, "Contraseña y/o correo incorrectos", Toast.LENGTH_SHORT).show();
               }
           });
    }


    private void comprobarRole(String uid) {
        DocumentReference docRef = firestoredb.collection("Usuarios").document(uid);
        //comprobamos los datos del documento
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                //Identificamos el rol del usuario
                if(documentSnapshot.getString("role").equals("Organizador")){
                    startActivity(new Intent(LoginActivity.this, OrganizadorActivity.class));
                    finish();

                }if(documentSnapshot.getString("role").equals("Jefe de Equipo")){
                    startActivity(new Intent(LoginActivity.this, ActivityJefeEquipo.class));
                    finish();

                }if(documentSnapshot.getString("role").equals("Piloto")){
                    startActivity(new Intent(LoginActivity.this, ActivityPiloto.class));
                    finish();
                }
            }
        });
    }


    public void RecuperarClave(View view) {
        Intent intent = new Intent(this, RecuperarClaveActivity.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("Usuarios")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getString("role").equals("Organizador")){
                        startActivity(new Intent(LoginActivity.this, OrganizadorActivity.class));
                        finish();

                    }if(documentSnapshot.getString("role").equals("Jefe de Equipo")){
                        startActivity(new Intent(LoginActivity.this, ActivityJefeEquipo.class));
                        finish();

                    }if (documentSnapshot.getString("role").equals("Piloto")){
                        startActivity(new Intent(LoginActivity.this, ActivityPiloto.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            });
        }
    }
}