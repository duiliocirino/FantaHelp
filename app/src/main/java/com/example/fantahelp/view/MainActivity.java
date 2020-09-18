package com.example.fantahelp.view;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fantahelp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureTextActions();
    }

    private void configureTextActions() {
        TextView newGame = findViewById(R.id.start);
        newGame.setOnClickListener((e)-> startActivity(new Intent(MainActivity.this, NewGameActivity.class)));
    }


}