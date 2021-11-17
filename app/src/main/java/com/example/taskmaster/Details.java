package com.example.taskmaster;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// this is Entity to create table with each column of our data

@Entity
public class Details {
    @PrimaryKey
    public Long uid;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "body")
    public String body;
    @ColumnInfo(name = "state")
    public String state;

    public Details(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getState() {
        return state;
    }
}
