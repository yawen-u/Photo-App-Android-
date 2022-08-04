package com.example.gridview;

import androidx.annotation.IntegerRes;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    public String albumName;
    public ArrayList<Photo> photoList;
    public int numPhotos; // num photos in an an album
    public Integer albumIcon;

    public Album(String albumName) {
        this.albumName = albumName;
        this.photoList = new ArrayList<Photo>();
        this.numPhotos = 0;
        this.albumIcon = 0; // Display the first photo of the album as its icon
    }

    public void addPhoto(Photo photo) {
        photo.id = numPhotos;
        this.photoList.add(photo);
        this.numPhotos++;
    }

    public void removePhoto(Integer fileName) {
        this.photoList.remove(getPhoto(fileName));
        if(numPhotos != 0)
            this.numPhotos--;
    }

    public Photo getPhoto(Integer fileName) {
        for(int i = 0; i < photoList.size(); i++) {
            if(photoList.get(i).image.equals(fileName)) {
                return photoList.get(i);
            }
        }
        return null;
    }

    public boolean hasPhoto(Integer fileName) {
        if (getPhoto(fileName) != null)
            return true;
        else
            return false;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getNumPhotos() {
        int temp = 0;
        for(int i = 0; i < photoList.size(); i++) {
            temp++;
        }
        numPhotos = temp;
        return this.numPhotos;
    }

    public ArrayList<Photo> getPhotoList(){
        return this.photoList;
    }

    public void setPhotoAlbum(ArrayList<Photo> album){
        this.photoList = album;
    }

    public String toString() {
        return albumName;
    }
}
