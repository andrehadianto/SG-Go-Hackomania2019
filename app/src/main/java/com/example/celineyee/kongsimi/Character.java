package com.example.celineyee.kongsimi;

import android.os.Parcel;
import android.os.Parcelable;

public class Character {
    private String name;
    private String description;
    private int thumbnail;

    public Character(){
    }

    public Character(String name, String description, int thumbnail){
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    protected Character(Parcel in) {
        name = in.readString();
        description = in.readString();
        thumbnail = in.readInt();
    }

    public String getName() {return name;}
    public String getDescription() {return description;}
    public int getThumbnail() {return thumbnail;}

    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setThumbnail(int thumbnail) {this.thumbnail = thumbnail;}
}
