package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private EditText txt_nombre = null;
    private EditText txt_nickname = null;
    private EditText txt_correo = null;
    private EditText txt_password = null;
    private Button btn_registro;
    private  FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseReference;

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

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance("https://simracingmanagement-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = txt_nombre.getText().toString();
                nickname = txt_nickname.getText().toString();
                correo = txt_correo.getText().toString();
                contraseña = txt_password.getText().toString();

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
                    //creamos un mapa de valores
                    Map<String, Object> map = new HashMap<>();
                    map.put("Nombre", nombre);
                    map.put("Nickname", nickname);
                    map.put("Email", correo);
                    map.put("Contraseña", contraseña);

                    String id = firebaseAuth.getCurrentUser().getUid();

                    mDatabaseReference.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this, RoleActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistroActivity.this, "No se pudo crear los datos en correctamente", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }else {
                    Toast.makeText(RegistroActivity.this, "No se ha podido crear al usuario", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


}