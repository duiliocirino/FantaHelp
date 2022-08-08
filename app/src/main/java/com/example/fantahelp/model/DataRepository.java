package com.example.fantahelp.model;

import android.app.Application;
import android.telecom.Call;
import androidx.lifecycle.LiveData;
import com.example.fantahelp.model.entities.Game;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.Team;
import com.example.fantahelp.model.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase db;

    private LiveData<List<Player>> allPlayers;
    private LiveData<List<User>> allUsers;
    private LiveData<List<Game>> allGames;
    private LiveData<List<Team>> allTeams;


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

    private int futureIntHandler(Callable<Integer> insertCallable){
        int value = -1;

        Future<Integer> future = AppDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            value = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value;
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

    public LiveData<List<Team>> getAllTeams(List<Integer> ids) {
        if(allTeams == null)
            allTeams = db.teamDao().loadAllByIds(ids);
        return allTeams;
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

    public void updateUser(User newUser) {
        AppDatabase.databaseWriteExecutor.execute(() -> db.userDao().updateUser(newUser));
    }

    public int createGame(Game newGame) {
        Callable<Long> insertCallable = () -> db.gameDao().insertGame(newGame);
        return futureHandler(insertCallable);
    }

    public int addNewUser(User newUser) {
        Callable<Long> insertCallable = () -> db.userDao().insertUser(newUser);
        return futureHandler(insertCallable);
    }

    public int addNewTeam(Team newTeam) {
        Callable<Long> insertCallable = () -> db.teamDao().insertTeam(newTeam);
        return futureHandler(insertCallable);
    }

    public int getSquadRating(String name) {
        Callable<Integer> insertCallable = () -> db.serieATeamDao().getRatingByName(name);
        return futureIntHandler(insertCallable);
    }

    public boolean assignPlayer(Team team, Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            db.teamDao().updateTeam(team);
            db.playerDao().updatePlayer(player);
        });
        return true;
    }

    public Player getPlayerByName(String playerName) {
        try {
            return allPlayers.getValue().stream().filter(x -> x.name.equals(playerName)).collect(Collectors.toList()).get(0);
        } catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    public Team getTeamById(int team_id) {
        try {
            return allTeams.getValue().stream().filter(x -> x.id == team_id).collect(Collectors.toList()).get(0);
        } catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    public User getUserByName(String userName) {
        try {
            return allUsers.getValue().stream().filter(x -> x.name.equals(userName)).collect(Collectors.toList()).get(0);
        } catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    public void updatePlayers(List<Team> nonNullTeams) {
        List<Player> playersToUpdate = new ArrayList<>();
        for(Team team: nonNullTeams){
            playersToUpdate.addAll(allPlayers.getValue().stream()
                    .filter(x -> team.players_id.contains(x.id))
                    .collect(Collectors.toList()));
            playersToUpdate.stream().forEach(x -> x.ownerId = team.user_id);
        }
        AppDatabase.databaseWriteExecutor.execute(() -> db.playerDao().updatePlayers(playersToUpdate));
    }

    public List<Player> getPlayersByUserTeam(String username) {
        User user = getUserByName(username);
        Team team = getTeamById(user.team_id);
        return getPlayersByTeam(team);
    }

    public List<Player> getPlayersByTeam(Team team) {
        return getAllPlayers().getValue().stream()
                .filter(x -> team.players_id.contains(x.id))
                .sorted((a, b) -> b.role.compareTo(a.role))
                .collect(Collectors.toList());
    }

    public Team getMyTeam() {
        if(allTeams.getValue() == null) return null;
        return allTeams.getValue().get(0);
    }
}
