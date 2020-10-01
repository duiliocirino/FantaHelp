package com.example.fantahelp.model.entities;

import androidx.room.Entity;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public User(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
