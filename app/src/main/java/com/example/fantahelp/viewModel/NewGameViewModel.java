package com.example.fantahelp.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.example.fantahelp.model.DataRepository;
import com.example.fantahelp.model.entities.Game;
import com.example.fantahelp.model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class NewGameViewModel extends AndroidViewModel {
    private final DataRepository mRepository;
    private String gameName;
    private List<String> usernames = new ArrayList<>();

    public NewGameViewModel(Application application) {
        super(application);

        mRepository = DataRepository.getRepository(application);
    }

    public boolean addUsername(String name) {
        if(!usernames.contains(name)){
            usernames.add(name);
            return true;
        }
        return false;
    }

    public void setGameName(String name){
        this.gameName = name;
    }

    public int createGame() {
        int gameId = -1;
        if(gameName != null) {
            if (!gameName.isEmpty() && usernames.size() > 3 && usernames.size() < 11) {
                List<Integer> userIds = new ArrayList<>();
                List<User> newUsers = new ArrayList<>();
                for (String username : usernames) {
                    //TODO:reset of the added users "transactions"
                    User newUser = new User(username);
                    int id = mRepository.addNewUser(newUser);
                    newUser.setId(id);
                    newUsers.add(newUser);
                    userIds.add(newUser.id);
                }
                Game newGame = new Game(gameName, userIds);
                gameId = mRepository.createGame(newGame);
            }
        }
        return gameId;
    }
}
