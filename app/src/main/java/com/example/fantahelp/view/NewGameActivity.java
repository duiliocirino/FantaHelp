package com.example.fantahelp.view;

import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fantahelp.R;

import java.util.ArrayList;
import java.util.List;

public class NewGameActivity extends AppCompatActivity {

    List<String> usernames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_new_game);

        configureInput();
    }

    private void configureInput() {
        EditText input = findViewById(R.id.usernameInput);
        input.setOnEditorActionListener((e1,e2,e) -> {
            String name = input.getText().toString();
            if(!usernames.contains(name)){
                usernames.add(name);
                TextView nameTV = new TextView(getApplicationContext(), null, 0, R.style.UsernameBoxStyle);
                nameTV.setText(name);
                LinearLayout scrollLayout = findViewById(R.id.scrollLayout);
                scrollLayout.addView(nameTV);
            }
            return true;
        });
    }
}