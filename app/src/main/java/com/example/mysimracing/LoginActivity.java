package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysimracing.Clases.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_correo_inicio = null;
    private EditText edt_password_inicio = null;
    private Button btn_acceder;

    private FirebaseAuth firebaseAuth;

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

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference usersRef = rootRef.collection("Usuarios");
        Query query = usersRef.whereEqualTo("Email", email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String email = document.getString("Email");
                        String password = document.getString("Contraseña");
                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                usersRef.document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                                        if (task2.isSuccessful()) {
                                            DocumentSnapshot document = task2.getResult();
                                            if (document.exists()) {
                                                String role = document.getString("Role");
                                                if(role.equals("Organizador")) {
                                                    startActivity(new Intent(LoginActivity.this, OrganizadorActivity.class));
                                                    Toast.makeText(LoginActivity.this, "Bienvenido Organizador", Toast.LENGTH_SHORT).show();
                                                    finish();

                                                } else if (role.equals("Jefe de Equipo")) {
                                                    startActivity(new Intent(LoginActivity.this, ActivityJefeEquipo.class));
                                                    Toast.makeText(LoginActivity.this, "Bienvenido Jefe", Toast.LENGTH_SHORT).show();
                                                    finish();

                                                } else if (role.equals("Piloto")){
                                                    startActivity(new Intent(LoginActivity.this, ActivityPiloto.class));
                                                    Toast.makeText(LoginActivity.this, "Bienvenido Piloto", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
           /*firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));



                    }else {
                        Toast.makeText(LoginActivity.this, "Acceso denegado, correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }

               }
           });*/
    }

    public void RecuperarClave(View view) {
        Intent intent = new Intent(this, RecuperarClaveActivity.class);
        startActivity(intent);
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() !=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }*/
}