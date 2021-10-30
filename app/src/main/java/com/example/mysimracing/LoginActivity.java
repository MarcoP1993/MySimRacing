package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private FirebaseAuth firebaseAuth;

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
        edt_correo_inicio = findViewById(R.id.correo_inicio);
        edt_password_inicio = findViewById(R.id.password_inicio);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void registro(View view) {
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public void acceso(View view) {
           Intent intent = new Intent(this,MainActivity.class);
           startActivity(intent);
    }

    public void RecuperarClave(View view) {
        Intent intent = new Intent(this, RecuperarClaveActivity.class);
        startActivity(intent);
    }

}