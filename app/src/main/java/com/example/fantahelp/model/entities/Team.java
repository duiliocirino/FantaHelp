package com.example.fantahelp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.fantahelp.model.utils.IntegerConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int user_id;
    public int numPlayers = 0;
    public int numP = 0;
    public int numD = 0;
    public int numC = 0;
    public int numA = 0;
    @TypeConverters(IntegerConverter.class)
    public List<Integer> players_id;
    public int credits;

    public Team(int id) {
        this.user_id = id;
        credits = 500;
        players_id = new ArrayList<>();
    }

    public Team(){
        players_id = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }
}
