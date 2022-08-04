package com.example.gridview;

import android.net.Uri;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Photo implements Serializable {
    protected Uri path;
    protected Integer image;
    protected long id;
    protected String caption;
    protected ArrayList<Tag> tagList;
    protected Calendar date;

    public Photo(Integer image) {
        this.image = image;
        this.caption = "";
        this.tagList = new ArrayList<Tag>();
    }

    public Photo(Uri path) {
        this.path = path;
        this.caption = "";
        this.tagList = new ArrayList<Tag>();
    }

    public long getId(){
        return id;
    }

    public void addTag(String type, String value) {
        this.tagList.add(new Tag(type, value));
    }

    public void deleteTag(String tagType, String tagValue) {
        this.tagList.remove(getTag(tagType, tagValue));
    }

    public Tag getTag(String tagType, String tagValue) {
        for(int i = 0; i < tagList.size(); i++) {
            if (tagList.get(i).type.equals(tagType) && tagList.get(i).value.equals(tagValue)) {
                return tagList.get(i);
            }
        }
        return null;
    }

    public Integer getFile(){
        return this.image;
    }

    public Uri getPath(){
        return this.path;
    }

    public ArrayList<Tag> getTagList() {
        return this.tagList;
    }

    public void setTagList(ArrayList<Tag> temp) {
        this.tagList = temp;
    }

    public void printTagList() {
        // Print location tag first
        for(int i = 0; i < tagList.size(); i++) {
            if(tagList.get(i).type.equalsIgnoreCase("location"))
                System.out.println(tagList.get(i).type + ":" + tagList.get(i).value);
        }

        // Print person tags next
        for(int i = 0; i < tagList.size(); i++) {
            if(tagList.get(i).type.equalsIgnoreCase("person"))
                System.out.println(tagList.get(i).type + ":" + tagList.get(i).value);
        }

        // Print any other tags last
        for(int i = 0; i < tagList.size(); i++) {
            if(tagList.get(i).type.equalsIgnoreCase("location") || tagList.get(i).type.equalsIgnoreCase("person"))
                continue;
            else
                System.out.println(tagList.get(i).type + ":" + tagList.get(i).value);
        }

        if(tagList.size() == 0) {
            System.out.println("No tags exist.");
        }
    }

    public boolean hasTag(Photo photo, Tag tag) {
        // This for loop makes "currTag" the instance and "tagList" the list to be iterated through
        for(Tag currTag : tagList) {
            if (currTag.equals(tag))
                return true;
        }
        return false;
    }

    public void editCaption(Photo photo, String newCaption) {
        this.caption = newCaption;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Calendar getDate(){
        return this.date;
    }

    public String toString() {
        return this.path.toString();
    }
}
