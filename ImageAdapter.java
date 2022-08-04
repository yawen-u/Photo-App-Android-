package com.example.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Integer> album_grid_images;
    private LayoutInflater layoutInflater;

    public ImageAdapter(Context mContext, ArrayList<Integer> albums) {
        this.context = mContext;
        this.album_grid_images = albums;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return album_grid_images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {  // if it's not recycled, initialize some attributes
            view = layoutInflater.inflate(R.layout.row_items, parent, false);
        }

        TextView albumName = view.findViewById(R.id.albumName);
        ImageView albumImage = view.findViewById(R.id.albumImage);

        albumName.setText(UserAlbumActivity.album_list.get(position).albumName);
        albumImage.setImageResource(album_grid_images.get(position));
        return view;
    }

    public Integer getImgID(int position){
        return 0;
    }

}
