package com.example.gridview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserAlbumActivity extends AppCompatActivity {

    public GridView albumGrid;
    private ImageAdapter img_adapter;
    public static ArrayList<Album> album_list;
    public static Album curr_album;

    private Integer[] mThumbIds = {
                        R.drawable.sample_2, R.drawable.sample_3,
                        R.drawable.sample_4, R.drawable.sample_5,
                        R.drawable.sample_6, R.drawable.sample_7,
                        R.drawable.sample_0, R.drawable.sample_1,
                        R.drawable.sample_2, R.drawable.sample_3,
                        R.drawable.sample_4, R.drawable.sample_5,
                        R.drawable.sample_6, R.drawable.sample_7,
                        R.drawable.sample_0, R.drawable.sample_1,
                        R.drawable.sample_2, R.drawable.sample_3,
                        R.drawable.sample_4, R.drawable.sample_5,
                        R.drawable.sample_6, R.drawable.sample_7
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album);
        Intent album_i = getIntent();
        setTitle("Albums");

        // initialize user's album list
        album_list = new ArrayList<Album>();
//        Album test_album = new Album("Stock");
//        album_list.add(test_album);
//        for (Integer i : mThumbIds) {
//            Photo photo = new Photo(i);
//            test_album.photoList.add(photo);
//        }
//        test_album.albumIcon = test_album.photoList.get(0).image;

        // get album's icons and save them as an array
        ArrayList<Integer> album_icons = new ArrayList<>(album_list.size());
        for (Album a : album_list) {
            album_icons.add(a.albumIcon);
        }

        ImageAdapter img_adapter = new ImageAdapter(this, album_icons);
        albumGrid = (GridView) findViewById(R.id.album_gridview);
        albumGrid.setAdapter(img_adapter);
        albumGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curr_album = album_list.get(position);
                openAlbum();
            }
        });

        Toolbar toolbar = findViewById(R.id.album_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addOption:  {
                // Create a new album with default name and icon
                Album new_album = new Album("Rename your new Album");
                new_album.albumIcon = R.drawable.album_placeholder;

                // Add album to the grid
                album_list.add(new_album);

                // Get album's icons again and save them as an array
                ArrayList<Integer> album_icons = new ArrayList<>(album_list.size());
                for (Album a : album_list) {
                    album_icons.add(a.albumIcon);
                }

                // Adapt images to Gridview
                ImageAdapter img_adapter = new ImageAdapter(this, album_icons);
                albumGrid = (GridView) findViewById(R.id.album_gridview);
                albumGrid.setAdapter(img_adapter);

                return true;
            }
            case R.id.searchOption: {
                Intent searchAct = new Intent(this, SearchActivity.class);
                startActivity(searchAct);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openAlbum() {
        Intent open_album = new Intent(this, UserPhotosActivity.class);
        startActivity(open_album);
    }

}