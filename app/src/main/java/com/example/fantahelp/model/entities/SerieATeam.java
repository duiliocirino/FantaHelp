package com.example.fantahelp.model.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SerieATeam {
    @PrimaryKey
    @NonNull
    public String name;
    public int rating;

    public SerieATeam(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }
}
