package com.example.fantahelp.model.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import com.example.fantahelp.model.entities.SerieATeam;

import java.util.List;

@Dao
public interface SerieATeamDao {
    @Insert
    void insertAll(List<SerieATeam> serieATeams);
}
