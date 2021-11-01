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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
           firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    }else {
                        Toast.makeText(LoginActivity.this, "Acceso denegado, correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }  
               }
           });
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