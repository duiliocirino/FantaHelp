package com.example.fantahelp.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.fantahelp.model.DataRepository;
import com.example.fantahelp.model.entities.Game;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.Team;
import com.example.fantahelp.model.entities.User;

import java.util.List;
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
        if(allUsers.getValue() != null) {
            allTeams = dataRepository.getAllTeams(allUsers.getValue().stream().map(x -> x.team_id).collect(Collectors.toList()));
        }
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

    public boolean assignPlayer(String userName, String playerName, int bet) {
        if(dataRepository.assignPlayer(userName, playerName, bet)) return true;
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