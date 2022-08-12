package com.example.fantahelp.model.daos;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.fantahelp.model.entities.Player;

import java.util.List;

@Dao
public interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Player> players);

    @Insert
    void insert(Player player);

    @Update
    void updatePlayer(Player player);

    @Query("SELECT * FROM Player")
    LiveData<List<Player>> getAllPlayers();

    @Update
    void updatePlayers(List<Player> playersToUpdate);
}
