package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarClaveActivity extends AppCompatActivity {

    private EditText edt_correo_recuperacion;
    private Button btn_recuperar_contraseña;

    private String email = "";

    private FirebaseAuth firebaseAuth;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_clave);

        edt_correo_recuperacion = (EditText) findViewById(R.id.edt_email_recuperacion);
        btn_recuperar_contraseña = (Button) findViewById(R.id.btn_enviar_mail);

        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        btn_recuperar_contraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = edt_correo_recuperacion.getText().toString();

                if(!email.isEmpty()){

                    dialog.setMessage("Enviando, espere por favor...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    resetPassword();

                }else{
                    Toast.makeText(RecuperarClaveActivity.this, "Introduce un email", Toast.LENGTH_SHORT).show();
                }

                
            }
        });
    }

    public void resetPassword(){
        firebaseAuth.setLanguageCode("es");

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(RecuperarClaveActivity.this, "Correo enviado correctamente", Toast.LENGTH_SHORT).show();
                    
                }else {
                    Toast.makeText(RecuperarClaveActivity.this, "No se pudo enviar el correo", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
    }
}