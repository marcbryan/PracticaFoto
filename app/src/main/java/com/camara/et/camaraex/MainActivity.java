package com.camara.et.camaraex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private Intent takePictureIntent;
    private File photoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer foto con camara
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                System.out.println("onClick");
                //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));


                    }catch (IOException e){
                        e.printStackTrace();
                    }

                //}
            }
        });
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File("/storage/sdcard/Android/data/com.camara.et.camaraex/files/Pictures");//ruta donde se guardara la foto
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Pone la foto que he realizado en el ImageView
            ImageView foto = findViewById(R.id.foto);
            foto.setImageBitmap(imageBitmap);
            //Guarda la imagen
            /*
            try {
                photoFile = createImageFile();
            } catch (IOException e){
                e.printStackTrace();
            }*/
            Uri uriSavedImage = Uri.fromFile(photoFile);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Imagen guardada correctamente!");
            builder.setTitle("Información");
            builder.create();
            builder.show();
        }
    }
}
