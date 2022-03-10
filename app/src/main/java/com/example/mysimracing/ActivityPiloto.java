package com.example.mysimracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ActivityPiloto extends AppCompatActivity {

    private CircularImageView circularImageView;
    String nombre, nick;
    private TextView txt_nom_piloto, txt_nick_piloto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piloto);
        circularImageView = findViewById(R.id.img_perfil_piloto);
        txt_nom_piloto = findViewById(R.id.txt_nom_piloto);
        txt_nick_piloto = findViewById(R.id.txt_nick_piloto);

        Intent intent = getIntent();
        nombre = intent.getStringExtra("Nombre");
        nick = intent.getStringExtra("nickname");
        txt_nom_piloto.setText(nombre);
        txt_nick_piloto.setText(nick);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.perfil_menu){
            startActivity(new Intent(ActivityPiloto.this, PerfilUsuarioActivity.class));

        }else if (id == R.id.info_menu){

        }else if (id == R.id.cerrar_sesion){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ActivityPiloto.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}