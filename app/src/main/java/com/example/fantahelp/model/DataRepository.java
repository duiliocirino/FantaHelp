package com.example.fantahelp.model;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.fantahelp.model.entities.Game;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase db;

    private LiveData<List<Player>> allPlayers;
    private LiveData<List<User>> allUsers;
    private LiveData<List<Game>> allGames;


    DataRepository(Application application) throws IOException {
        db = AppDatabase.getDatabase(application);
    }

    public static DataRepository getRepository(Application application) {
        if(sInstance == null){
            try {
                sInstance = new DataRepository(application);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sInstance;
    }


    public void addNewUsers(List<User> newUsers) {
        db.userDao().insertAll(newUsers);
    }

    public LiveData<List<Player>> getAllPlayers() {
        if(allPlayers == null)
            allPlayers = db.playerDao().getAllPlayers();
        return allPlayers;
    }

    private Game futureGameHandler(Callable<Game> insertCallable){
        Game game = null;

        Future<Game> future = AppDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            game = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return game;
    }

    public LiveData<List<User>> getAllUsers(int gameId) {
        Callable<Game> insertCallable = () -> db.gameDao().getGameaById(gameId);
        if(allUsers == null)
            allUsers = db.userDao().loadAllByIds(futureGameHandler(insertCallable).userIds.stream().mapToInt(i -> i).toArray());
        return allUsers;
    }

    public LiveData<List<Game>> getAllGames() {
        if(allGames == null)
            allGames = db.gameDao().getAll();
        return allGames;
    }

    public LiveData<Game> getGameById(int gameId) {
        return db.gameDao().getGameById(gameId);
    }

    /**
     * This method manages the returning of single ids from insertion.
     * @param insertCallable
     * @return
     */
    private int futureHandler(Callable<Long> insertCallable) {
        long rowId = 0;

        Future<Long> future = AppDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return (int) rowId;
    }

    public int createGame(Game newGame) {
        Callable<Long> insertCallable = () -> db.gameDao().insertGame(newGame);
        return futureHandler(insertCallable);
    }

    public int addNewUser(User newUser) {
        Callable<Long> insertCallable = () -> db.userDao().insertUser(newUser);
        return futureHandler(insertCallable);
    }

    public Game getGameaById(int gameId) {
        return db.gameDao().getGameaById(gameId);
    }
}
