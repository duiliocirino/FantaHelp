package com.example.fantahelp.model.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import com.example.fantahelp.model.entities.Game;

@Dao
public interface GameDao {
    @Insert
    void insertGame(Game game);
}
