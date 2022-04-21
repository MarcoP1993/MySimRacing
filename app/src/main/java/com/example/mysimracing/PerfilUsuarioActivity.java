package com.example.mysimracing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mikhaellopez.circularimageview.CircularImageView;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private EditText edt_nombre_perfil;
    private EditText edt_nick_perfil;
    private TextView nombre_perfil, nick_perfil;
    private CircularImageView imagenUsuario;
    private RadioGroup rg_roles_perfil = null;
    private RadioButton rb_organizador_perfil;
    private RadioButton rb_jefe_equipo_perfil;
    private RadioButton rb_piloto_perfil;

    private Button actualizar_perfil;

    private String idUsuario;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoredb;

    private String nombre = "";
    private String nickname = "";
    private String correo = "";
    private String role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        edt_nombre_perfil = (EditText) findViewById(R.id.edt_nombre);
        edt_nick_perfil = (EditText) findViewById(R.id.edt_Nickname);
        rg_roles_perfil = (RadioGroup) findViewById(R.id.radio_group_roles_perfil);
        rb_organizador_perfil = (RadioButton) findViewById(R.id.Rb_organizador_perfil);
        rb_jefe_equipo_perfil = (RadioButton) findViewById(R.id.Rb_jefe_equipo_perfil);
        rb_piloto_perfil = (RadioButton) findViewById(R.id.Rb_piloto_perfil);
        actualizar_perfil = (Button) findViewById(R.id.btn_act_perfil);
        nombre_perfil = (TextView) findViewById(R.id.txt_nombre_perfil);
        nick_perfil = (TextView) findViewById(R.id.txt_nick_perfil);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser usuario = firebaseAuth.getCurrentUser();
        firestoredb = FirebaseFirestore.getInstance();
        idUsuario = firebaseAuth.getCurrentUser().getUid();


        DocumentReference docRef = firestoredb.collection("Usuarios").document(idUsuario);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot , @Nullable FirebaseFirestoreException error) {
                nombre_perfil.setText("Nombre: " + documentSnapshot.getString("nombre"));
                nick_perfil.setText("Nick: " + documentSnapshot.getString("nickname"));
            }
        });

        //boton actualizar
    }

    //creamos el metodo actualizar perfil usuario
}