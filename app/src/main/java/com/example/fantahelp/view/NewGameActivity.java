package com.example.fantahelp.view;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
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

        newGameViewModel = ViewModelProviders.of(this).get(NewGameViewModel.class);

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
                //TODO: Snackbar showing the user cannot be added
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
        if(newGameViewModel.createGame()){

        } else{
            //TODO: Snackbar showing the game cannot be created
        }
    }
}