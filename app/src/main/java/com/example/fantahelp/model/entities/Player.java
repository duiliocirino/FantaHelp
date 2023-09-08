package com.example.fantahelp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey
    public int id;
    public String role;
    public String name;
    public String squad;
    public int price;
    public int myRating;
    public String mate;
    public int regularness;
    public int fvm;
    public int expPrice;

    public Player(int id, String role, String name, String squad, int price, int myRating, String mate, int regularness,
                  int fvm, int expPrice) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.squad = squad;
        this.price = price;
        this.myRating = myRating;
        this.mate = mate;
        this.regularness = regularness;
        this.fvm = fvm;
        this.expPrice = expPrice;
    }
}