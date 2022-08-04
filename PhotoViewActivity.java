package com.example.gridview;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.util.concurrent.atomic.AtomicInteger;

public class PhotoViewActivity extends AppCompatActivity {

    AtomicInteger position;

    ImageView imageView;
    BottomNavigationItemView forward;
    BottomNavigationItemView back;
    BottomNavigationItemView edit;

    // Pop Up Properties
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog copyDialog;
    private AlertDialog moveDialog;
    private AlertDialog addTagDialog;
    private AlertDialog deleteTagDialog;
    private AlertDialog renameDialog;

    private EditText copy_dest_album;
    private EditText move_dest_album;
    private AutoCompleteTextView addTag_type;
    private EditText addTag_value;
    private AutoCompleteTextView deleteTag_type;
    private EditText deleteTag_value;
    private EditText rename;

    private Button copy_save, copy_cancel;
    private Button move_save, move_cancel;
    private Button addTag_save, addTag_cancel;
    private Button rename_save, rename_cancel;
    private Button deleteTag_save, deleteTag_cancel;

    private Photo curr_photo;
    private static final String[] tag_types = new String[]{"person", "location"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photoviewtool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.removePhoto){
            //TODO remove photo
            return true;
        }
        else if (item.getItemId() == R.id.movePhoto){
            MovePhotoToAlbum();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view_activity);
        imageView = findViewById(R.id.photoImageView);
        forward = findViewById(R.id.forwardPhoto);
        back = findViewById(R.id.backPhoto);
        edit = findViewById(R.id.editPhoto);
        Intent i = getIntent();
        position = new AtomicInteger(i.getIntExtra("position", -1));
        setPhotoView(position.get());

        forward.setOnClickListener(view -> {
             if(position.get() == UserAlbumActivity.curr_album.photoList.size() - 1){
                 position.set(0);
             } else{
                 position.incrementAndGet();
             }
             setPhotoView(position.get());
        });

        back.setOnClickListener(view -> {
             if(position.get() == 0){
                 position.set(UserAlbumActivity.curr_album.photoList.size() - 1);
             } else{
                 position.decrementAndGet();
             }
             setPhotoView(position.get());
        });

        edit.setOnClickListener(view -> AddTagToPhoto());
    }

    protected void setPhotoView(int position){
        imageView.setImageURI(UserAlbumActivity.curr_album.photoList.get(position).getPath());
    }

    public void CopyPhotoToAlbum () {

        Photo selected = curr_photo;
        dialogBuilder = new AlertDialog.Builder(PhotoViewActivity.this);
        final View copyPhotoPopUp = getLayoutInflater().inflate(R.layout.copy_popup, null);

        copy_dest_album = (EditText) findViewById(R.id.destAlbumInput);
        copy_save = (Button) findViewById(R.id.copy_saveButton);
        copy_cancel = (Button) findViewById(R.id.copy_cancelButton);

        dialogBuilder.setView(copyPhotoPopUp);
        copyDialog = dialogBuilder.create();
        copyDialog.show();

        copy_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if the destination album name is valid
                for  (Album album : UserAlbumActivity.album_list) {

                    if (album.getAlbumName().equals(copy_dest_album.getText().toString())) {
                        Album destination = album;
                        if (destination.hasPhoto(selected.image)) {
                            Toast.makeText(getApplicationContext(), "Photo already exist in the destination album.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        destination.addPhoto(selected);
                        return;
                    }
                }
            }
        });

        copy_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyDialog.dismiss();
            }
        });


    }

    public void MovePhotoToAlbum () {

        Photo selected = curr_photo;
        dialogBuilder = new AlertDialog.Builder(this);
        final View movePhotoPopUp = getLayoutInflater().inflate(R.layout.move_popup, null);

        move_dest_album = (EditText) findViewById(R.id.destAlbumInput);
        move_save = (Button) findViewById(R.id.move_saveButton);
        move_cancel = (Button) findViewById(R.id.move_cancelButton);

        dialogBuilder.setView(movePhotoPopUp);
        moveDialog = dialogBuilder.create();
        moveDialog.show();

//        move_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // Check if user input is empty
//                if (move_dest_album.getText().toString().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Empty Input.",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // check if the destination album name is valid
//                for (Album album : UserAlbumActivity.album_list) {
//
//                    if (album.getAlbumName().equals(move_dest_album.getText().toString())) {
//                        Album destination = album;
//                        if (destination.hasPhoto(selected.image)) {
//                            Toast.makeText(getApplicationContext(), "Photo already exist in the destination album.",
//                                    Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        destination.addPhoto(selected);
//                        UserAlbumActivity.curr_album.removePhoto(selected.image);
//                        return;
//                    }
//                }
//
//                // TODO Update GridView
//            }
//        });
//
//
//        move_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                moveDialog.dismiss();
//            }
//        });
    }


    public void AddTagToPhoto () {

        Photo selected = curr_photo;
        dialogBuilder = new AlertDialog.Builder(PhotoViewActivity.this);
        final View addTagPopUp = getLayoutInflater().inflate(R.layout.add_tag_popup, null);

        // Tag Type Auto Complete Text Input
        addTag_type = (AutoCompleteTextView) findViewById(R.id.addTagTypeInput);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tag_types);
        addTag_type.setAdapter(adp1);

        addTag_value = (EditText) findViewById(R.id.addTagValueInput);
        addTag_save = (Button) findViewById(R.id.addTag_saveButton);
        addTag_cancel = (Button) findViewById(R.id.addTag_cancelButton);

        dialogBuilder.setView(addTagPopUp);
        addTagDialog = dialogBuilder.create();
        addTagDialog.show();

        addTag_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagType = addTag_type.getText().toString();
                String tagValue = addTag_value.getText().toString();
                if (tagType.isEmpty() || tagValue.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Inputs.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Photo selected = curr_photo;

                //check if tag already exists
                if (selected.getTag(tagType, tagValue) == null){
                    selected.addTag(tagType, tagValue);
                }

                //TODO update tag list display
            }
        });

        addTag_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTagDialog.dismiss();
            }
        });
    }

    public void renameAlbum () {

        dialogBuilder = new AlertDialog.Builder(PhotoViewActivity.this);
        final View renamePopUp = getLayoutInflater().inflate(R.layout.rename_popup, null);

        rename = (EditText) findViewById(R.id.renameInput);
        rename_save = (Button) findViewById(R.id.rename_saveButton);
        rename_cancel = (Button) findViewById(R.id.rename_cancelButton);

        dialogBuilder.setView(renamePopUp);
        renameDialog = dialogBuilder.create();
        renameDialog.show();

        rename_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rename.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Input.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                UserAlbumActivity.curr_album.albumName = rename.getText().toString();

                // reload the activity
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

        rename_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameDialog.dismiss();
            }
        });
    }

    public void deleteTag(){

        Photo selected = curr_photo;
        dialogBuilder = new AlertDialog.Builder(PhotoViewActivity.this);
        final View deleteTagPopUp = getLayoutInflater().inflate(R.layout.delete_tag_popup, null);

        // Tag Type Auto Complete Text Input
        deleteTag_type = (AutoCompleteTextView) findViewById(R.id.deleteTag_typeInput);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tag_types);
        deleteTag_type.setAdapter(adp2);

        deleteTag_value = (EditText) findViewById(R.id.deleteTag_valueInput);
        deleteTag_save = (Button) findViewById(R.id.deleteTag_saveButton);
        deleteTag_cancel = (Button) findViewById(R.id.deleteTag_cancelButton);

        dialogBuilder.setView(deleteTagPopUp);
        deleteTagDialog = dialogBuilder.create();
        deleteTagDialog.show();

        deleteTag_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagType = deleteTag_type.getText().toString();
                String tagValue = deleteTag_value.getText().toString();
                if (tagType.isEmpty() || tagValue.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Inputs.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Photo selected = curr_photo;

                //check if tag already exists
                if (selected.getTag(tagType, tagValue) == null){
                    Toast.makeText(getApplicationContext(), "This tag doesn't exist.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                selected.deleteTag(tagType, tagValue);

                //TODO update tag list display
            }
        });

        deleteTag_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTagDialog.dismiss();
            }
        });

    }
}
