package com.example.fantahelp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.fantahelp.model.utils.IntegerConverter;
import com.example.fantahelp.model.utils.FloatConverter;

import java.util.List;

@Entity
public class Player {
    @PrimaryKey
    public int id;
    public int ownerId;
    public String role;
    public String name;
    public String squad;
    public int price;
    public int myRating;
    public String mate;
    public int regularness;
    public int fvm;
    public int expPrice;
    // various stats of previous years
    @TypeConverters(IntegerConverter.class)
    public List<Integer> gamesPlayed;
    @TypeConverters(FloatConverter.class)
    public List<Float> avgVote;
    @TypeConverters(FloatConverter.class)
    public List<Float> avgFantaVote;
    @TypeConverters(IntegerConverter.class)
    public List<Integer> amm;

    public Player(int id, int ownerId, String role, String name, String squad, int price, int myRating, String mate, int regularness,
                  int fvm, int expPrice, List<Integer> gamesPlayed, List<Float> avgVote, List<Float> avgFantaVote, List<Integer> amm) {
        this.id = id;
        this.ownerId = ownerId;
        this.role = role;
        this.name = name;
        this.squad = squad;
        this.price = price;
        this.myRating = myRating;
        this.mate = mate;
        this.regularness = regularness;
        this.fvm = fvm;
        this.expPrice = expPrice;
        this.gamesPlayed = gamesPlayed;
        this.avgVote = avgVote;
        this.avgFantaVote = avgFantaVote;
        this.amm = amm;
    }
}