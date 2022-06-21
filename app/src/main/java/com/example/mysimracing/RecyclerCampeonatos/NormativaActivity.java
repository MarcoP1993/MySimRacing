package com.example.mysimracing.RecyclerCampeonatos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysimracing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class NormativaActivity extends AppCompatActivity {

    EditText edt_normativa;
    TextView txt_normativa;
    Button btn_cambiarNormativa;
    String normativa = "";

    private String idUsuario;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoredb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normativa);
        edt_normativa = findViewById(R.id.edt_normativa);
        txt_normativa = findViewById(R.id.txt_normativa);
        btn_cambiarNormativa = findViewById(R.id.btn_editar_normativa);

        firebaseAuth = FirebaseAuth.getInstance();
        firestoredb = FirebaseFirestore.getInstance();
        idUsuario = firebaseAuth.getCurrentUser().getEmail();


        btn_cambiarNormativa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normativa = edt_normativa.getText().toString();
                txt_normativa.setText("Pincha en el enlace para ver la normativa: " + "\n" + normativa);

                guardardatos();
            }
        });

        DocumentReference docRef = firestoredb.collection("Normativa").document(idUsuario);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txt_normativa.setText("Pulsa el enlace para ver la normativa: " + value.getString("Enlace"));
            }
        });
        
    }
    
    public void guardardatos(){
        Map<String, Object> normativas = new HashMap<>();
        normativas.put("Enlace", normativa);
        normativas.put("id", idUsuario);
        
        firestoredb.collection("Normativa").document(idUsuario).set(normativas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(NormativaActivity.this, "Enlace guardado correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("Usuarios")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("role").equals("Organizador")) {
                        btn_cambiarNormativa.setVisibility(View.VISIBLE);
                        edt_normativa.setVisibility(View.VISIBLE);

                    } if (documentSnapshot.getString("role").equals("Jefe de Equipo")) {
                        btn_cambiarNormativa.setVisibility(View.INVISIBLE);
                        edt_normativa.setVisibility(View.INVISIBLE);
                    }
                    if (documentSnapshot.getString("role").equals("Piloto")) {
                        btn_cambiarNormativa.setVisibility(View.INVISIBLE);
                        edt_normativa.setVisibility(View.INVISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}