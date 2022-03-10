package com.example.mysimracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ActivityJefeEquipo extends AppCompatActivity {

    private CircularImageView circularImageView;
    private TextView nombreJefe;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jefe_equipo);
        circularImageView = findViewById(R.id.img_perfil_jefe);
        nombreJefe = findViewById(R.id.txt_nombreEquipo);

        Intent intent = getIntent();
        nombre = intent.getStringExtra("Nombre");
        nombreJefe.setText(nombre);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.perfil_menu){
            startActivity(new Intent(ActivityJefeEquipo.this, PerfilUsuarioActivity.class));

        }else if (id == R.id.info_menu){

        }else if (id == R.id.cerrar_sesion){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ActivityJefeEquipo.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}