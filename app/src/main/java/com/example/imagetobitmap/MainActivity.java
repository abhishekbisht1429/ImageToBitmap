package com.example.imagetobitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    public static final int IMAGE_OPEN_REQUEST = 1;
    Button buttonSelect;
    TextView textViewPath;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSelect = findViewById(R.id.button_select);
        textViewPath = findViewById(R.id.text_view_file_path);
        imageView = findViewById(R.id.image_view);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.setType("image/*");
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i,IMAGE_OPEN_REQUEST);
            }
        });
    }

    void showToast(String msg,boolean lengthLong) {
        Toast.makeText(this,msg,lengthLong?Toast.LENGTH_LONG:Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(requestCode == IMAGE_OPEN_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            String imagePath = imageUri.getPath();
            showToast(imagePath,true);
            textViewPath.setText(imagePath);
            try {
                InputStream imgInputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(imgInputStream);
                imageView.setImageBitmap(bitmap);
            }catch(IOException ioE) {
                ioE.printStackTrace();
            }catch(OutOfMemoryError error) {
                error.printStackTrace();
                showToast("Image File too large!!",false);
            }
        }
    }
}
