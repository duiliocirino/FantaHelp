package com.example.fantahelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.fantahelp.R;
import com.example.fantahelp.model.AppDatabase;
import com.example.fantahelp.model.entities.Player;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
        newGame.setOnClickListener((e)-> startActivity(new Intent(MainActivity.this, NewGameActivity.class)));
    }


}