package com.example.fantahelp.viewModel.factories;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.fantahelp.viewModel.GameViewModel;

public class GameViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private int gameId;


    public GameViewModelFactory(Application application, int gameId) {
        mApplication = application;
        this.gameId = gameId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new GameViewModel(mApplication, gameId);
    }
}
