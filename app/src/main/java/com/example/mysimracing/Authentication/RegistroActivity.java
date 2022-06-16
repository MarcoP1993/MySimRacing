package com.example.mysimracing.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mysimracing.Clases.Usuarios;
import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText txt_nombre = null;
    private EditText txt_nickname = null;
    private EditText txt_correo = null;
    private EditText txt_password = null;
    private RadioGroup rg_roles = null;
    private RadioButton rb_organizador;
    private RadioButton rb_jefe_equipo;
    private RadioButton rb_piloto;

    private String idUsuario;
    private Button btn_registro;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoredb;

    //Variables de los datos a regitrar
    private String nombre = "";
    private String nickname = "";
    private String correo = "";
    private String contraseña = "";
    private String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txt_nombre = (EditText) findViewById(R.id.edt_nombre);
        txt_nickname = (EditText) findViewById(R.id.edt_Nickname);
        txt_correo = (EditText) findViewById(R.id.edt_email_registro);
        txt_password = (EditText) findViewById(R.id.edt_pass_registro);
        btn_registro = (Button) findViewById(R.id.btn_registrarse);
        rg_roles = (RadioGroup) findViewById(R.id.radio_group_roles_perfil);
        rb_organizador = (RadioButton) findViewById(R.id.Rb_organizador_perfil);
        rb_jefe_equipo = (RadioButton) findViewById(R.id.Rb_jefe_equipo_perfil);
        rb_piloto = (RadioButton) findViewById(R.id.Rb_piloto_perfil);


        firebaseAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();

        //logica RadioBox



        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = txt_nombre.getText().toString();
                nickname = txt_nickname.getText().toString();
                correo = txt_correo.getText().toString();
                contraseña = txt_password.getText().toString();
                role = "";

                if(!nombre.isEmpty() && !nickname.isEmpty() && !correo.isEmpty() && !contraseña.isEmpty()){

                    if(contraseña.length() >= 6) {


                        if(!(rb_organizador.isChecked() || rb_jefe_equipo.isChecked() || rb_piloto.isChecked())){

                            Toast.makeText(RegistroActivity.this, "Selecciona un rol", Toast.LENGTH_SHORT).show();
                            return;

                        }else{
                            elegirRole();
                            registrarse();
                        }


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
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Toast.makeText(RegistroActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    DocumentReference documentReference = firestoredb.collection("Usuarios").document(user.getUid());

                    Usuarios usuarios = new Usuarios(nombre,nickname,correo, contraseña, role, null);
                    //EventosTorneo eventos = new EventosTorneo("nombre 1", "seseña", "ps4", false);
                    //creamos un mapa de valores
                    /*Map<String, Object> user = new HashMap<>();
                    user.put("Nombre", nombre);
                    user.put("Nickname", nickname);
                    user.put("Email", correo);
                    user.put("Contraseña", contraseña);
                    user.put("Role", role);*/

                    documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"Perfil del usuario creado " + user);
                        }
                    });
                }else {
                    Toast.makeText(RegistroActivity.this, "El usuario ya existe, intentalo con otro correo", Toast.LENGTH_LONG).show();

                }
            }
        });
    }




    public void elegirRole (){
        int seleccionadoOk = rg_roles.getCheckedRadioButtonId();

        if(seleccionadoOk == R.id.Rb_organizador_perfil){

            role = "Organizador";
            Toast.makeText(this,"Has seleccionado Organizador",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            String nombre = txt_nombre.getText().toString();
            intent.putExtra("Nombre", nombre);
            startActivity(intent);

        }else if(seleccionadoOk == R.id.Rb_jefe_equipo_perfil){

            role = "Jefe de Equipo";
            Toast.makeText(this,"Has seleccionado Jefe de equipo",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            String nombre = txt_nombre.getText().toString();
            intent.putExtra("Nombre", nombre);
            startActivity(intent);

        }else if (seleccionadoOk == R.id.Rb_piloto_perfil){

            role = "Piloto";
            Toast.makeText(this,"Has seleccionado Piloto",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            String nombre = txt_nombre.getText().toString();
            String nick = txt_nickname.getText().toString();
            intent.putExtra("Nombre", nombre);
            intent.putExtra("nickname", nick);
            startActivity(intent);

        }else {
            Toast.makeText(RegistroActivity.this, "Selecciona un rol", Toast.LENGTH_SHORT).show();

        }

        }
    }


