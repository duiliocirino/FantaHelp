package com.example.fantahelp.view;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fantahelp.R;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.utils.DecisionMaker;
import com.example.fantahelp.model.utils.GameRVAdapter;
import com.example.fantahelp.model.utils.LoadRVAdapter;
import com.example.fantahelp.model.utils.ValueCalculator;
import com.example.fantahelp.viewModel.GameViewModel;

import java.util.stream.Collectors;


public class GameActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener{

    GameViewModel gameViewModel;

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

            //HomeFragment firstFragment = new HomeFragment();
            //getSupportFragmentManager().beginTransaction().add(R.id.fragment, firstFragment).commit();
            //Spinner of users

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item);

            //SearchBar

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

            final GameRVAdapter mAdapter = new GameRVAdapter(this);

            gameViewModel.getAllPlayers().observe(this, players -> {
                players.sort( (a, b) -> b.price - a.price);

                RecyclerView recyclerView = findViewById(R.id.suggestionBox);
                recyclerView.setHasFixedSize(true);

                AutoCompleteTextView searchBar = findViewById(R.id.playerSearchBar);

                arrayAdapter.addAll(players.stream().map(x -> x.name).toArray(String[]::new));
                searchBar.setAdapter(arrayAdapter);

                if(recyclerView.getAdapter() == null) {
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }

                mAdapter.setNames(players.stream().map(x -> x.name).collect(Collectors.toList()));
                mAdapter.setSquadNames(players.stream().map(x -> x.squad).collect(Collectors.toList()));
                mAdapter.setRegular(players.stream().map(x -> x.regularness).collect(Collectors.toList()));
                mAdapter.setMyVote(players.stream().map(x -> x.myRating).collect(Collectors.toList()));
                mAdapter.setValues(ValueCalculator.getValues(getApplication(), players, gameViewModel.getSelectedRole()));
                mAdapter.setVisibility(players.stream().map(x -> x.role.equals("P")).collect(Collectors.toList()));
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setButtons();
    }

    private void setButtons(){
        Button button = findViewById(R.id.buttonPlus5);
        button.setOnClickListener((e) -> {
            EditText editText = findViewById(R.id.editTextBetNumber);
            int bet = Integer.parseInt(editText.getText().toString());
            editText.setText(String.valueOf(bet + 5));
        });
        button = findViewById(R.id.buttonPlus1);
        button.setOnClickListener((e) -> {
            EditText editText = findViewById(R.id.editTextBetNumber);
            int bet = Integer.parseInt(editText.getText().toString());
            editText.setText(String.valueOf(bet + 1));
        });
        button = findViewById(R.id.buttonMinus1);
        button.setOnClickListener((e) -> {
            EditText editText = findViewById(R.id.editTextBetNumber);
            int bet = Integer.parseInt(editText.getText().toString());
            if(bet - 1 > 0) {
                editText.setText(String.valueOf(bet - 1));
            }
            else {
                Toast.makeText(this, R.string.negativeNum, 1).show();
            }
        });
        button = findViewById(R.id.buttonMinus5);
        button.setOnClickListener((e) -> {
            EditText editText = findViewById(R.id.editTextBetNumber);
            int bet = Integer.parseInt(editText.getText().toString());
            if(bet - 5 > 0) {
                editText.setText(String.valueOf(bet - 5));
            }
            else {
                Toast.makeText(this, R.string.negativeNum, 1).show();
            }
        });
        button = findViewById(R.id.keeperButton);
        Button finalButton = button;
        button.setOnClickListener((e) -> {
            TextView textView = findViewById(R.id.roleTextView);

            textView.setText(finalButton.getText());
            gameViewModel.setSelectedRole(finalButton.getText().toString());

            RecyclerView recyclerView = findViewById(R.id.suggestionBox);
            GameRVAdapter gameRVAdapter = (GameRVAdapter) recyclerView.getAdapter();
            gameRVAdapter.setVisibility(gameViewModel.getAllPlayers().getValue().stream()
                    .map(x -> x.role.equals("P"))
                    .collect(Collectors.toList()));
        });
        button = findViewById(R.id.defenderButton);
        Button finalButton1 = button;
        button.setOnClickListener((e) -> {
            TextView textView = findViewById(R.id.roleTextView);
            textView.setText(finalButton1.getText());
            gameViewModel.setSelectedRole(finalButton1.getText().toString());

            RecyclerView recyclerView = findViewById(R.id.suggestionBox);
            GameRVAdapter gameRVAdapter = (GameRVAdapter) recyclerView.getAdapter();
            gameRVAdapter.setVisibility(gameViewModel.getAllPlayers().getValue().stream()
                    .map(x -> x.role.equals("D"))
                    .collect(Collectors.toList()));
        });
        button = findViewById(R.id.midfielderButton);
        Button finalButton2 = button;
        button.setOnClickListener((e) -> {
            TextView textView = findViewById(R.id.roleTextView);
            textView.setText(finalButton2.getText());
            gameViewModel.setSelectedRole(finalButton2.getText().toString());

            RecyclerView recyclerView = findViewById(R.id.suggestionBox);
            GameRVAdapter gameRVAdapter = (GameRVAdapter) recyclerView.getAdapter();
            gameRVAdapter.setVisibility(gameViewModel.getAllPlayers().getValue().stream()
                    .map(x -> x.role.equals("C"))
                    .collect(Collectors.toList()));
        });
        button = findViewById(R.id.attackerButton);
        Button finalButton3 = button;
        button.setOnClickListener((e) -> {
            TextView textView = findViewById(R.id.roleTextView);
            textView.setText(finalButton3.getText());
            gameViewModel.setSelectedRole(finalButton3.getText().toString());

            RecyclerView recyclerView = findViewById(R.id.suggestionBox);
            GameRVAdapter gameRVAdapter = (GameRVAdapter) recyclerView.getAdapter();
            gameRVAdapter.setVisibility(gameViewModel.getAllPlayers().getValue().stream()
                    .map(x -> x.role.equals("A"))
                    .collect(Collectors.toList()));
        });

        AutoCompleteTextView searchBar = findViewById(R.id.playerSearchBar);

        searchBar.setOnItemClickListener((parent, view, position, id) -> {
            decisionViewsHandler(searchBar);
        });

        EditText editTextNumber = findViewById(R.id.editTextBetNumber);
        editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                decisionViewsHandler(searchBar);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                decisionViewsHandler(searchBar);
            }

            @Override
            public void afterTextChanged(Editable s) {
                decisionViewsHandler(searchBar);
            }
        });
        editTextNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                decisionViewsHandler(searchBar);
                return true;
            }
        });
    }

    private void decisionViewsHandler(AutoCompleteTextView searchBar) {
        String playerName = searchBar.getText().toString();
        Player player = gameViewModel.getPlayerByName(playerName);
        EditText editTextNumber = findViewById(R.id.editTextBetNumber);
        TextView decisionView = findViewById(R.id.decisionView);
        TextView myVoteView = findViewById(R.id.myVoteView);
        TextView valueView = findViewById(R.id.valueView);
        int suggestedValue = ValueCalculator.getValue(player, getApplication());
        String decision = DecisionMaker.makeDecision(player, suggestedValue,
                Integer.parseInt(editTextNumber.getText().toString()));

        decisionView.setText(decision);
        if(decision.equals("PASS")) decisionView.setTextColor(Color.RED);
        else decisionView.setTextColor(Color.GREEN);
        myVoteView.setText(String.valueOf(player.myRating));
        valueView.setText(String.valueOf(suggestedValue));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}