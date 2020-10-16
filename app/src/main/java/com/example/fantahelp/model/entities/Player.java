package com.example.fantahelp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey
    public int id;
    public int ownerId = -1;
    public String role;
    public String name;
    public String squad;
    public int price;
    public int myRating;
    public String mate;
    public int regularness;
    // various stats of previous years
    public int pg19_20;
    public float mv19_20;
    public float mf19_20;
    public int assists19_20;
    public int amm19_20;
    public int pg18_19;
    public float mv18_19;
    public float mf18_19;
    public int assists18_19;
    public int amm18_19;

    public Player(int id, String role, String name, String squad, int price, int myRating, String mate, int regularness, int pg19_20, float mv19_20, float mf19_20, int assists19_20, int amm19_20, int pg18_19, float mv18_19, float mf18_19, int assists18_19, int amm18_19) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.squad = squad;
        this.price = price;
        this.myRating = myRating;
        this.mate = mate;
        this.regularness = regularness;
        this.pg19_20 = pg19_20;
        this.mv19_20 = mv19_20;
        this.mf19_20 = mf19_20;
        this.assists19_20 = assists19_20;
        this.amm19_20 = amm19_20;
        this.pg18_19 = pg18_19;
        this.mv18_19 = mv18_19;
        this.mf18_19 = mf18_19;
        this.assists18_19 = assists18_19;
        this.amm18_19 = amm18_19;
    }
}