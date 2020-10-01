package com.example.fantahelp.model.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.fantahelp.model.entities.Game;

import java.util.List;

@Dao
public interface GameDao {
    @Insert
    long insertGame(Game game);

    @Query("SELECT * FROM Game")
    LiveData<List<Game>> getAll();

    @Query("SELECT * FROM Game WHERE id = :gameId")
    LiveData<Game> getGameById(int gameId);

    @Query("SELECT * FROM Game WHERE id = :gameId")
    Game getGameaById(int gameId);
}
