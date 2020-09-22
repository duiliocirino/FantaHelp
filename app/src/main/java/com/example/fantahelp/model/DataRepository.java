package com.example.fantahelp.model;

import android.app.Application;
import com.example.fantahelp.model.entities.Game;
import com.example.fantahelp.model.entities.User;

import java.util.List;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase db;

    DataRepository(Application application) {
        db = AppDatabase.getDatabase(application);
    }

    public static DataRepository getRepository(Application application) {
        if(sInstance == null){
            sInstance = new DataRepository(application);
        }
        return sInstance;
    }


    public boolean createGame(Game newGame) {
        db.gameDao().insertGame(newGame);
        return true;
    }

    public void addNewUsers(List<User> newUsers) {
        db.userDao().insertAll(newUsers);
    }
}
