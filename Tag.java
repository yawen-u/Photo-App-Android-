package com.example.gridview;

import java.io.Serializable;

public class Tag implements Serializable {
    protected String type;
    protected String value;
    protected String tagString;

    public Tag(String tagType, String tagValue) {
        this.type = tagType;
        this.value = tagValue;
        this.tagString = type + "=" + value;
    }

    public String getType(){
        return this.type;
    }

    public String getValue(){
        return this.value;
    }

    public String getTagString() {
        return tagString;
    }
}
