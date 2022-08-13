package com.example.fantahelp.view;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fantahelp.R;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.Team;
import com.example.fantahelp.model.utils.CreditsCalculator;
import com.example.fantahelp.model.utils.DecisionMaker;
import com.example.fantahelp.model.utils.GameRVAdapter;
import com.example.fantahelp.model.utils.ValueCalculator;
import com.example.fantahelp.view.ui.DashboardFragment;
import com.example.fantahelp.view.ui.HomeFragment;
import com.example.fantahelp.viewModel.GameViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.stream.Collectors;


public class GameActivity extends FragmentActivity{

    GameViewModel gameViewModel;

    HomeFragment homeFragment;
    DashboardFragment dashboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();
        int gameId;

        if(bundle != null) {


            gameId = bundle.getInt("gameId");

            int finalGameId = gameId;
            ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
                @NonNull
                @Override
                public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                    return (T) new GameViewModel(getApplication(),
                            finalGameId);
                }
            };

            gameViewModel = new ViewModelProvider(this, factory)
                    .get(GameViewModel.class);

            //FRAGMENT HANDLING


            homeFragment = new HomeFragment();
            openFragment(homeFragment);

            BottomNavigationView bottomNavigationItemView = findViewById(R.id.bottomNavigationView);
            bottomNavigationItemView.setOnNavigationItemSelectedListener(item -> {

                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:
                        openFragment(homeFragment);
                        return true;
                    case R.id.bottomNavigationDashboardMenuId:
                        if(dashboardFragment == null) dashboardFragment = new DashboardFragment();
                        openFragment(dashboardFragment);
                        return true;
                    //case R.id.bottomNavigationSettingsMenuId: openFragment(new SettingsFragment());
                }
                return false;
            });
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}