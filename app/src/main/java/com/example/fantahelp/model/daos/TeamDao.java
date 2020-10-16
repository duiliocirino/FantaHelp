package com.example.fantahelp.model.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.fantahelp.model.entities.Team;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert
    Long insertTeam(Team newTeam);

    @Insert
    void insertAllTeams(List<Team> teams);

    @Query("SELECT * FROM Team WHERE user_id IN (:ids)")
    LiveData<List<Team>> loadAllByIds(List<Integer> ids);

    @Update
    void updateTeam(Team team);
}
