package com.example.gridview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView tagType;
    private AutoCompleteTextView tagValue;
    private GridView searchGrid;

    private static final String[] tag_types = new String[]{"person", "location"};
    public ArrayList<String> tag_values = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search By Tags");

        tagType = findViewById(R.id.tagTypeInput);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tag_types);
        tagType.setAdapter(adp1);

        tagValue = findViewById(R.id.tagValueInput);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tag_values);
        tagValue.setAdapter(adp2);

        // Tool bar to go back to the home page
        Toolbar toolbar = findViewById(R.id.album_toolbar);

        // Display search results
        searchGrid = findViewById(R.id.search_result_gridview);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if (item.getItemId() == R.id.homeOption) {
           finish();
       }
       return true;
    }

    private void search(){

        ArrayList<Photo> results = new ArrayList<Photo>();
        String type = tagType.getText().toString();
        String value = tagValue.getText().toString();
        if (type.isEmpty() && value.isEmpty()) {
            Toast.makeText(this, "Empty Inputs", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!type.isEmpty() && value.isEmpty()){
            for (Photo photo : UserAlbumActivity.curr_album.getPhotoList()) {
                for (Tag tag : photo.getTagList()) {
                    if (tag.type == type) {
                        results.add(photo);
                        break;
                    }
                }
            }
        } else if (type.isEmpty() && !value.isEmpty()) {
            for (Photo photo : UserAlbumActivity.curr_album.getPhotoList()) {
                for (Tag tag : photo.getTagList()) {
                    if ((tag.value == value) || tag.value.contains(value)) {
                        results.add(photo);
                        break;
                    }
                }
            }
        }

        ArrayList<Integer> result_images = new ArrayList<Integer>();
        for (Photo p : results) {
            result_images.add(p.image);
        }
        ImageAdapter img_adapter = new ImageAdapter(SearchActivity.this, result_images);
        searchGrid.setAdapter(img_adapter);
    }

}