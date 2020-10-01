package com.example.fantahelp.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.fantahelp.model.DataRepository;
import com.example.fantahelp.model.entities.Game;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.User;

import java.util.List;

public class GameViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private DataRepository dataRepository;

    private LiveData<Game> game;
    private LiveData<List<Player>> allPlayers;
    private LiveData<List<User>> allUsers;

    public GameViewModel(Application application, int gameId) {
        super(application);
        dataRepository = DataRepository.getRepository(application);
        game = dataRepository.getGameById(gameId);
        allPlayers = dataRepository.getAllPlayers();
        allUsers = dataRepository.getAllUsers(gameId);
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
}