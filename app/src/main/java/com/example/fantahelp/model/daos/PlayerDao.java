package com.example.fantahelp.model.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.example.fantahelp.model.entities.Player;

import java.util.List;

@Dao
public interface PlayerDao {
    @Insert
    void insertAll(List<Player> players);
}
