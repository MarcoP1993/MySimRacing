package com.example.mysimracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mysimracing.RecicledListas.EventosTorneo;
import com.example.mysimracing.RecicledListas.ListaEventosAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements Serializable {

    //private TextView nombre_usuario;
    //private TextView nickname_usuario;

    private ArrayList<EventosTorneo> eventos = null;
    private RecyclerView rv_eventos = null;
    private ListaEventosAdapter adaptadorEventos;

    //private FirebaseAuth firebaseAuth;
    //private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebaseAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        //nombre_usuario  = (TextView) findViewById(R.id.txt_nombre_usuario);
        //nickname_usuario = (TextView) findViewById(R.id.txt_nickname_usuario);

        rv_eventos = (RecyclerView) findViewById(R.id.rv_eventos);

        eventos = new ArrayList<EventosTorneo>();
        eventos.add(new EventosTorneo("Torneo 1", "Seseña", "PS4", false));
        eventos.add(new EventosTorneo("Torneo 2", "Seseña", "XBOX", true));
        eventos.add(new EventosTorneo("Torneo 3", "Seseña", "PS5",true));
        eventos.add(new EventosTorneo("Torneo 4", "Seseña", "PC", false));
        eventos.add(new EventosTorneo("Torneo 5", "Seseña", "PS4", true));
        eventos.add(new EventosTorneo("Torneo 6", "Seseña", "PS5", false));
        eventos.add(new EventosTorneo("Torneo 7", "Seseña", "XBOX", false));
        eventos.add(new EventosTorneo("Torneo 8", "Seseña", "PC", true));

        adaptadorEventos = new ListaEventosAdapter(this,eventos);
        rv_eventos.setAdapter(adaptadorEventos);
        rv_eventos.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(eventos, from, to);
                adaptadorEventos.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.LEFT)
                {
                    mostrarToast("ha pulsado izquierda");
                    // Ciudad c = ciudades.get(viewHolder.getAdapterPosition());
                    // CiudadController.borrarCiudad(c);
                    eventos.remove(viewHolder.getAdapterPosition());
                    adaptadorEventos.notifyItemRemoved(viewHolder.getAdapterPosition());
                }
                if(direction == ItemTouchHelper.RIGHT)
                {
                    mostrarToast("ha pulsado derecha");
                    eventos.remove(viewHolder.getAdapterPosition());
                    adaptadorEventos.notifyItemRemoved(viewHolder.getAdapterPosition());
                }
            }
        });
        helper.attachToRecyclerView(rv_eventos);
    }

    public void mostrarEventos(EventosTorneo e) {
        Toast.makeText(this,"Nombre -> " + e.getNombreTorneo() + "\n" + "Ciudad -> "+ e.getCiudad() + "\n"+ "Plataforma -> " + e.getPlataforma() + "\n" + "Empezado -> " + e.getCompitiendo(), Toast.LENGTH_LONG).show();
    }

    private void mostrarToast(String texto) {
        Toast.makeText(this,texto, Toast.LENGTH_SHORT).show();
    }
        //recuperarInfoUsusario();

    }

    /*public void recuperarInfoUsusario(){
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
    }*/
