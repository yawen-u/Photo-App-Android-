package com.example.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotosAdapter extends BaseAdapter {


        private Context context;
        private Album album;
        private LayoutInflater layoutInflater;

        public PhotosAdapter(Context mContext, Album albums) {
            this.context = mContext;
            this.album = albums;
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }



        public int getCount() {
            return album.photoList.size();
        }

        public Object getItem(int position) {
            return album.photoList.get(position);
        }

        public long getItemId(int position) {
            return album.photoList.get(position).getId();
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {  // if it's not recycled, initialize some attributes
                view = layoutInflater.inflate(R.layout.photos_row_items, parent, false);
            }

            ImageView photoImage = view.findViewById(R.id.photoImage);
            photoImage.setImageURI(album.photoList.get(position).getPath());
            return view;
        }

        public Integer getImgID(int position){
            return 0;
        }



}
