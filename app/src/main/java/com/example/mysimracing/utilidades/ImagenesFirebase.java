package com.example.mysimracing.utilidades;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ImagenesFirebase {


    FirebaseStorage storage;
    StorageReference storageRef;

    public interface FotoStatus
    {
        void FotoIsDownload(byte[] bytes);
        void FotoIsUpload();
        void FotoIsDelete();
    }

    public ImagenesFirebase() {
        this.storage  = FirebaseStorage.getInstance();
        this.storageRef = this.storage.getReference();
    }


    public void subirFoto(final ImagenesFirebase.FotoStatus fotoStatus, String email, String tipoImagen, ImageView img_add_foto) {
        //----------- convierto el imageView a Bitmap
        img_add_foto.setDrawingCacheEnabled(true);
        img_add_foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img_add_foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        // StorageReference imagesRef = storageRef.child("imagenes");
        StorageReference foto2Ref = storageRef.child(tipoImagen + "/" + email + tipoImagen +".png");
        UploadTask uploadTask = foto2Ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("firebasedb","la foto no se ha subido correctamente");

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.i("firebasedb","la foto se ha subido correctamente");
                fotoStatus.FotoIsUpload();
                // ...
            }
        });

    }

    public void descargarFoto(final ImagenesFirebase.FotoStatus fotoStatus, String foto) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(foto);

        final long tam_foto = 1024 * 1024; // tamaño máximo de la descarga de la imagen, si es mayor la descarga falla.
        islandRef.getBytes(tam_foto).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Log.i("firebasedb","foto descargada");
                fotoStatus.FotoIsDownload(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                byte[] bytes = null;
                Log.i("firebasedb","foto no descargada");
                int errorCode = ((StorageException) exception).getErrorCode();
                String errorMessage = exception.getMessage();
                Log.i("firebasedb",errorMessage);
                Log.i("firebasedb","error code" + String.valueOf(errorCode));
                fotoStatus.FotoIsDownload(bytes);
            }
        });

    }

    public void borrarFoto(final ImagenesFirebase.FotoStatus fotoStatus, String foto) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(foto);

        // Delete the file
        islandRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                Log.i("firebasedb","foto borrada correctamente");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Log.i("firebasedb","la foto no se pudo borrar");
            }
        });
    }


}
