package com.example.gridview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class UserPhotosActivity extends AppCompatActivity {

    public GridView photoView;
    public static Album test;
    int SELECT_PICTURE = 200;


    private PhotosAdapter img_adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photoadd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.addPhoto){
            imageChooser();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_photos);
//        setTitle(UserAlbumActivity.curr_album.albumName);

      //test = new Album("test");

        img_adapter = new PhotosAdapter(this, UserAlbumActivity.curr_album);
        photoView = findViewById(R.id.photoView);
        photoView.setAdapter(img_adapter);



        photoView.setOnItemClickListener((parent, v, position, id) -> {
            Intent i = new Intent(UserPhotosActivity.this, PhotoViewActivity.class);
            System.out.println(position);
            i.putExtra("position", position);
            startActivity(i);
        });


    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                UserAlbumActivity.curr_album.addPhoto(new Photo(selectedImageUri));
                photoView.setAdapter(img_adapter);


                }
            }
        }


}