package com.example.fantahelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fantahelp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureTextActions();


    }

    /**
     * This method gives to the "new game" text the desired behaviour to create the new activity.
     */
    private void configureTextActions() {
        TextView newGame = findViewById(R.id.start);
        newGame.setOnClickListener((e) -> startActivity(new Intent(MainActivity.this, NewGameActivity.class)));

        TextView load = findViewById(R.id.load);
        load.setOnClickListener((e) -> startActivity(new Intent(MainActivity.this, LoadActivity.class)));
    }
}