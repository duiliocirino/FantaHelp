package com.example.fantahelp.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.fantahelp.model.daos.PlayerDao;
import com.example.fantahelp.model.daos.UserDao;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.User;

@Database(entities = {User.class, Player.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PlayerDao playerDao();
}
