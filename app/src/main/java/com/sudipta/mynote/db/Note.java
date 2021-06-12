package com.sudipta.mynote.db;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;

    String title;

    @ColumnInfo(name = "body")
    String note;

    String imagePath;

    String color;

    String dateTime;

    public Note(String title, String note, String imagePath, String color,String dateTime) {
        this.title = title;
        this.note = note;
        this.imagePath = imagePath;
        this.color = color;
        this.dateTime = dateTime;
    }

//    public Note(String title, String note) {
//        this.title = title;
//        this.note = note;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return title + " : " + dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
