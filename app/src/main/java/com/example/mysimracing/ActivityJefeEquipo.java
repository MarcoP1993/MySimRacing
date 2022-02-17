package com.example.mysimracing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mikhaellopez.circularimageview.CircularImageView;

public class ActivityJefeEquipo extends AppCompatActivity {

    private CircularImageView circularImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jefe_equipo);
        circularImageView = findViewById(R.id.img_perfil_jefe);
    }
}