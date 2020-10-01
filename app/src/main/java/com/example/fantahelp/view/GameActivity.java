package com.example.fantahelp.view;

import android.view.View;
import android.widget.*;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.fantahelp.R;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.User;
import com.example.fantahelp.view.ui.HomeFragment;
import com.example.fantahelp.viewModel.GameViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GameActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener{

    GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();
        int gameId = -1;

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

            //HomeFragment firstFragment = new HomeFragment();
            //getSupportFragmentManager().beginTransaction().add(R.id.fragment, firstFragment).commit();
            //Spinner of users

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);

            //SearchBar

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

            gameViewModel.getAllPlayers().observe(this, players -> {
                AutoCompleteTextView searchBar = findViewById(R.id.playerSearchBar);
                arrayAdapter.addAll(players.stream().map(x -> x.name).toArray(String[]::new));
                searchBar.setAdapter(arrayAdapter);
            });
            gameViewModel.getGame().observe(this, game -> {

            });
            gameViewModel.getAllUsers().observe(this, users -> {
                Spinner spinner = findViewById(R.id.spinner);
                adapter.addAll(users.stream()
                        .map(x -> x.name)
                        .toArray(String[]::new));
                spinner.setAdapter(adapter);
            });
        } else{
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}