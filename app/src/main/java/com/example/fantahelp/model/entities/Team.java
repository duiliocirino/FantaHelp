package com.example.fantahelp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.fantahelp.model.utils.IntegerConverter;

import java.util.List;

@Entity
public class Team {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int user_id;
    @TypeConverters(IntegerConverter.class)
    public List<Integer> players_id;
}
