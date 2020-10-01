package com.example.fantahelp.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fantahelp.R;
import com.example.fantahelp.model.utils.LoadRVAdapter;
import com.example.fantahelp.viewModel.LoadActivityViewModel;

import java.util.stream.Collectors;

public class LoadActivity extends AppCompatActivity {

    private LoadActivityViewModel loadActivityViewModel;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setHasFixedSize(true);
        final LoadRVAdapter mAdapter = new LoadRVAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadActivityViewModel = new ViewModelProvider(this).get(LoadActivityViewModel.class);

        loadActivityViewModel.getAllGames().observe(this, games -> {
            mAdapter.setValues(games.stream()
                    .map(x-> x.name)
                    .collect(Collectors.toList()));
            mAdapter.setIds(games.stream()
                    .map(x -> x.id)
                    .collect(Collectors.toList()));
        });
    }
}