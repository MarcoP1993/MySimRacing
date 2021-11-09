package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText txt_nombre = null;
    private EditText txt_nickname = null;
    private EditText txt_correo = null;
    private EditText txt_password = null;

    private String idUsuario;
    private Button btn_registro;
    private ProgressBar progressBar;

    private  FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoredb;

    //Variables de los datos a regitrar
    private String nombre = "";
    private String nickname = "";
    private String correo = "";
    private String contraseña = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txt_nombre = (EditText) findViewById(R.id.edt_nombre);
        txt_nickname = (EditText) findViewById(R.id.edt_Nickname);
        txt_correo = (EditText) findViewById(R.id.edt_email_registro);
        txt_password = (EditText) findViewById(R.id.edt_pass_registro);
        btn_registro = (Button) findViewById(R.id.btn_registrarse);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = txt_nombre.getText().toString().trim();
                nickname = txt_nickname.getText().toString().trim();
                correo = txt_correo.getText().toString().trim();
                contraseña = txt_password.getText().toString().trim();

                if(!nombre.isEmpty() && !nickname.isEmpty() && !correo.isEmpty() && !contraseña.isEmpty()){

                    if(contraseña.length() >= 6) {

                        registrarse();

                    }else {
                        Toast.makeText(RegistroActivity.this, "La contraseña debe tener minimo 6 caracteres", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void registrarse() {
        firebaseAuth.createUserWithEmailAndPassword(correo,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistroActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    idUsuario = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = firestoredb.collection("Usuarios").document(idUsuario);

                    //creamos un mapa de valores
                    Map<String, Object> user = new HashMap<>();
                    user.put("Nombre", nombre);
                    user.put("Nickname", nickname);
                    user.put("Email", correo);
                    user.put("Contraseña", contraseña);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"Perfil del usuario creado " + idUsuario);
                        }
                    });
                    startActivity(new Intent(RegistroActivity.this, RoleActivity.class));
                    /*mDatabaseReference.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this, RoleActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistroActivity.this, "No se pudo crear los datos en correctamente", Toast.LENGTH_LONG).show();

                            }
                        }
                    });*/

                }else {
                    Toast.makeText(RegistroActivity.this, "No se ha podido crear al usuario", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


}