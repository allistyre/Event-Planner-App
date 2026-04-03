package com.example.a41p;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String category;
    public String location;
    public String dateTime;
}
