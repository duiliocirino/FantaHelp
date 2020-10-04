package com.example.fantahelp.view;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.example.fantahelp.R;
import com.example.fantahelp.viewModel.NewGameViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewGameActivity extends AppCompatActivity {

    NewGameViewModel newGameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_new_game);

        newGameViewModel = new ViewModelProvider(this).get(NewGameViewModel.class);

        configureInput();
    }

    private void configureInput() {
        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText gameNameInput = findViewById(R.id.newGameNameInput);

        usernameInput.setOnEditorActionListener((e1,e2,e) -> {
            String name = usernameInput.getText().toString();
            if(newGameViewModel.addUsername(name)){
                TextView nameTV = new TextView(getApplicationContext(), null, 0, R.style.UsernameBoxStyle);
                nameTV.setText(name);
                LinearLayout scrollLayout = findViewById(R.id.scrollLayout);
                scrollLayout.addView(nameTV);
            } else{
                //TODO: Toast showing the user cannot be added
            }
            return true;
        });
        gameNameInput.setOnEditorActionListener((e3, e, e1) ->{
            String name = gameNameInput.getText().toString();
            newGameViewModel.setGameName(name);
            return true;
        });

    }

    public void onNextButtonClicked(View view) {
        int gameId;

        EditText gameNameInput = findViewById(R.id.newGameNameInput);
        newGameViewModel.setGameName(gameNameInput.getText().toString());

        gameId = newGameViewModel.createGame();
        if(gameId != -1){
            Intent intent = new Intent(NewGameActivity.this, GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("gameId", gameId);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else{
            Toast.makeText(NewGameActivity.this, "You cannot create a game like this", 5).show();
            //startActivity(new Intent(NewGameActivity.this, GameActivity.class));

            //TODO: Toast showing the game cannot be created
        }
    }
}