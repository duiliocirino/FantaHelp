package com.example.fantahelp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.fantahelp.model.enums.Rate;

@Entity
public class Squad {
    @PrimaryKey
    public String name;
    public Rate rating;
}
