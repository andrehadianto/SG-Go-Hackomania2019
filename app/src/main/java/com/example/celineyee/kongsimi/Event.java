package com.example.celineyee.kongsimi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Event {
    private String name;
    private String startdate;
    private String enddate;
    private String text;
    private Character chara;

    public Event(){
    }

    public Event(String name, String startdate, String enddate, String text, Character chara){
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
        this.text = text;
        this.chara = chara;
    }

    public String getName() {return name;}
    public String getStartdate() {return startdate;}
    public String getEnddate() {return enddate;}
    public String getText() {return text;}
    public Character getChara() {return chara;}

    public void setName(String name) {this.name = name;}
    public void setStartdate(String startdate) {this.startdate = startdate;}
    public void setEnddate(String enddate) {this.enddate = enddate;}
    public void setText(String text) {this.text = text;}
    public void setChara(Character chara) {this.chara = chara;}

}
