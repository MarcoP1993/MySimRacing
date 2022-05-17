package com.example.mysimracing.RecicledListas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysimracing.Clases.Inscripciones;
import com.example.mysimracing.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class InscripcionesAdapter extends FirestoreRecyclerAdapter<Inscripciones, InscripcionesAdapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public InscripcionesAdapter(@NonNull FirestoreRecyclerOptions<Inscripciones> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Inscripciones inscripciones) {
            viewHolder.nombre.setText(inscripciones.getNombre());
            viewHolder.nick.setText(inscripciones.getNick());
            viewHolder.correo.setText(inscripciones.getCorreo());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //conectamos el layout de inscrpciones con el viewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inscripciones, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, nick, correo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_ins);
            nick = itemView.findViewById(R.id.nick_ins);
            correo =itemView.findViewById(R.id.correo_ins);

        }
    }
}
