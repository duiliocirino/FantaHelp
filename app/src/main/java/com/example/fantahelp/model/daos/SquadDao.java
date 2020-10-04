package com.example.fantahelp.model.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.fantahelp.model.entities.Squad;

import java.util.List;

@Dao
public interface SquadDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Squad> squads);

    @Query("SELECT rating FROM Squad WHERE name = :name")
    int getRatingByName(String name);
}
