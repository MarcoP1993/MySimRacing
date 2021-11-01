package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView nombre_usuario;
    private TextView nickname_usuario;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        nombre_usuario  = (TextView) findViewById(R.id.txt_nombre_usuario);
        nickname_usuario = (TextView) findViewById(R.id.txt_nickname_usuario);


        recuperarInfoUsusario();

    }

    public void recuperarInfoUsusario(){
        String id = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("Nombre").getValue().toString();
                    String nickname = snapshot.child("Nickname").getValue().toString();

                    nombre_usuario.setText(nombre);
                    nickname_usuario.setText(nickname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}