package com.example.fantahelp.model.entities;

import androidx.room.*;
import com.example.fantahelp.model.utils.IntegerConverter;

import java.util.List;

@Entity
public class Game {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    @TypeConverters(IntegerConverter.class)
    public List<Integer> userIds;

    @Ignore
    private final int MIN_NUMBER_USERS = 4;
    @Ignore
    private final int MAX_NUMBER_USERS = 10;
    @Ignore
    private final int FIXED_NUMBER_PLAYERS = 25;
    @Ignore
    private final int P_NUMBER = 3;
    @Ignore
    private final int D_NUMBER = 8;
    @Ignore
    private final int C_NUMBER = 8;
    @Ignore
    private final int A_NUMBER = 6;

    public Game(String name, List<Integer> userIds) {
        this.name = name;
        this.userIds = userIds;
    }
}
