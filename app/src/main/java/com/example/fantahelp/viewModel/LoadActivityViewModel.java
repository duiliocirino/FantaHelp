package com.example.fantahelp.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.fantahelp.model.DataRepository;
import com.example.fantahelp.model.entities.Game;

import java.util.List;

public class LoadActivityViewModel extends AndroidViewModel {
    private DataRepository dataRepository;

    private LiveData<List<Game>> allGames;

    public LoadActivityViewModel(Application application) {
        super(application);
        dataRepository = DataRepository.getRepository(application);
        allGames = dataRepository.getAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return allGames;
    }
}
