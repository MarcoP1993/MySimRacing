package com.example.mysimracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

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
    }

    public void registro(View view) {
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public void acceso(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void RecuperarClave(View view) {
        Intent intent = new Intent(this, RecuperarClaveActivity.class);
        startActivity(intent);
    }
}