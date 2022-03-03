package com.example.mysimracing.RecicledListas;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysimracing.Clases.Campeonatos;
import com.example.mysimracing.R;
import com.example.mysimracing.utilidades.ImagenesBlobBitmap;
import com.example.mysimracing.utilidades.ImagenesFirebase;

import java.util.ArrayList;

public class ListaEventosAdapter extends RecyclerView.Adapter<EventoViewHolder> {
    //atributos
    private Context c = null;
    private ArrayList<Campeonatos> campeonatos;
    private LayoutInflater le_inflater;

    public void setC(Context c) {
        this.c = c;
        this.campeonatos = new ArrayList<Campeonatos>();
    }

    public ListaEventosAdapter(Context context, ArrayList<Campeonatos> campeonatos) {
        this.c = context;
        this.campeonatos = campeonatos;
        le_inflater = LayoutInflater.from(c);
    }

    public Context getC() {
        return c;
    }

    public ArrayList<Campeonatos> getCampeonatos() {
        return campeonatos;
    }

    public void setEventos(ArrayList<Campeonatos> campeonatos) {
        this.campeonatos = campeonatos;
        notifyDataSetChanged();
    }

    public ListaEventosAdapter (Context c){
        this.c = c;
        le_inflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = le_inflater.inflate(R.layout.item_lista_eventos, parent, false);
        return new EventoViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        if (campeonatos != null) {
            Campeonatos campeonatoSeleccionado = campeonatos.get(position);
            holder.txt_competicion.setText("Nombre Competicion: " + campeonatoSeleccionado.getNombreCampeonato());
            holder.txt_juego.setText("Videojuego: " + campeonatoSeleccionado.getJuego());
            holder.txt_plataforma.setText("Plataforma: " + campeonatoSeleccionado.getPlataforma());
            holder.txt_fechas.setText("Fecha Competicion: " + campeonatoSeleccionado.getRango_fechas());
            if (campeonatoSeleccionado.getImagen() != null) {
                new ImagenesFirebase().descargarFoto(new ImagenesFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if (bytes != null) {
                            Log.i("firebasedb", "foto descargada correctamente");
                            Bitmap imagenb = ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray(bytes, ImagenesBlobBitmap.Configuracion.ALTO_IMAGENES_BITMAP, ImagenesBlobBitmap.Configuracion.ANCHO_IMAGENES_BITMAP);
                            holder.img_foto.setImageBitmap(imagenb);
                        }
                    }

                    @Override
                    public void FotoIsUpload() {

                    }

                    @Override
                    public void FotoIsDelete() {

                    }
                },campeonatoSeleccionado.getImagen());
            }
        }



    }

    @Override
    public int getItemCount() {
        if(campeonatos !=null)
            return campeonatos.size();
        else return 0;
    }
}
