package com.example.fantahelp.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.fantahelp.model.AppDatabase;
import com.example.fantahelp.model.DataRepository;
import com.example.fantahelp.model.entities.Game;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.Team;
import com.example.fantahelp.model.entities.User;
import com.example.fantahelp.model.utils.ValueCalculator;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private DataRepository dataRepository;

    private int myUserId = -1;
    private int myTeamId = -1;
    private String selectedRole = "P";

    public int getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(int myUserId) {
        this.myUserId = myUserId;
    }

    public int getMyTeamId() {
        return myTeamId;
    }

    public void setMyTeamId(int myTeamId) {
        this.myTeamId = myTeamId;
    }

    private LiveData<Game> game;
    private LiveData<List<Player>> allPlayers;
    private LiveData<List<User>> allUsers;
    private LiveData<List<Team>> allTeams;

    public GameViewModel(Application application, int gameId) {
        super(application);
        dataRepository = DataRepository.getRepository(application);
        game = dataRepository.getGameById(gameId);
        allPlayers = dataRepository.getAllPlayers();
        allUsers = dataRepository.getAllUsers(gameId);

    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public LiveData<List<Player>> getAllPlayers() {
        return allPlayers;
    }
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    public LiveData<Game> getGame() {
        return game;
    }
    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }

    public void retrieveAllTeams(List<Integer> ids) {
        allTeams = dataRepository.getAllTeams(ids);
    }

    public Team getMyTeam(List<Team> teams){
        for(Team team: teams){
            if(team.id == myTeamId) return team;
        }
        // to avoid NullPointerException
        return new Team(-1);
    }

    public Player getPlayerByName(String playerName) {
        try {
            return allPlayers.getValue().stream().filter(x -> x.name.equals(playerName)).collect(Collectors.toList()).get(0);
        } catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    public String getPlayerNameById(int id){
        return Objects.requireNonNull(allPlayers.getValue()).stream().filter(x -> x.id == id).findFirst().get().name;
    }

    public boolean assignPlayer(String userName, String playerName, int bet, boolean startup) {
        Player player = getPlayerByName(playerName);
        if(player == null) return false;
        User user = dataRepository.getUserByName(userName);
        if(user == null) return false;
        Team team = dataRepository.getTeamById(user.team_id);
        if(team == null) return false;
        if(team.credits - bet < 0) return false;
        team.credits = team.credits - bet;
        if(!startup) team.players_id.add(player.id);
        //TODO: player.ownerId = user.id;

        ValueCalculator.updateValues(getApplication(), Objects.requireNonNull(allPlayers.getValue()), player.role);

        if(dataRepository.assignPlayer(team, player) && !startup) return true;
        return false;
    }

    public void updatePlayers(List<Team> teams) {
        List<Team> nonNullTeams = teams.stream().filter(team -> team.players_id.size() > 0).collect(Collectors.toList());
        dataRepository.updatePlayers(nonNullTeams);
    }

    public List<Player> getPlayersByUserTeam(String username) {
        return dataRepository.getPlayersByUserTeam(username);
    }
}