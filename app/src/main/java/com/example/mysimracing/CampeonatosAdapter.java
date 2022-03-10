package com.example.mysimracing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.RecicledListas.EventoDetallesActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class CampeonatosAdapter extends RecyclerView.Adapter<CampeonatosAdapter.MyViewHolder> {

    ArrayList<Campeonatos> listaCampeonatos;
    Context context;

    public CampeonatosAdapter(ArrayList<Campeonatos> listaCampeonatos) {
        this.listaCampeonatos = listaCampeonatos;
    }

    public CampeonatosAdapter(ArrayList<Campeonatos> listaCampeonatos, Context context) {
        this.listaCampeonatos = listaCampeonatos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_eventos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {



        holder.txt_competicion.setText("Nombre Campeonato: " + listaCampeonatos.get(position).getNombreCampeonato());
        holder.txt_juego.setText("Videojuego: " + listaCampeonatos.get(position).getJuego());
        holder.txt_plataforma.setText("Plataforma: " + listaCampeonatos.get(position).getPlataforma());
        holder.txt_fechas.setText("Fecha Campeonato: " + listaCampeonatos.get(position).getRango_fechas());
        //Glide.with(context).load(listaCampeonatos.get(position).getImagen()).into(holder.imagen);

        holder.txt_competicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.txt_competicion.getContext(), EventoDetallesActivity.class);
                intent.putExtra("Nombre Campeonato: ", "Nombre Campeonato: " + listaCampeonatos.get(position).getNombreCampeonato());
                intent.putExtra("Fechas Campeonato: ", "Fecha Campeonato: " + "\n" + listaCampeonatos.get(position).getRango_fechas());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.txt_competicion.getContext().startActivity(intent);
                v.setOnClickListener(this);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCampeonatos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_competicion, txt_juego, txt_plataforma, txt_fechas;
        CircularImageView imagen;

        public MyViewHolder(@NonNull View view) {
            super(view);

            txt_competicion = (TextView) itemView.findViewById(R.id.txt_campeonato);
            txt_juego = (TextView) itemView.findViewById(R.id.txt_juego);
            txt_plataforma = (TextView) itemView.findViewById(R.id.txt_plataforma);
            txt_fechas = (TextView) itemView.findViewById(R.id.txt_fechas);
            imagen = itemView.findViewById(R.id.imageView8);

        }
    }
}
