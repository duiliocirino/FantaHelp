package com.example.fantahelp.view.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fantahelp.R;
import com.example.fantahelp.model.utils.DashboardRVAdapter;
import com.example.fantahelp.viewModel.GameViewModel;

import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GameViewModel gameViewModel;

    DashboardRVAdapter dashboardRVAdapter;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameViewModel = new ViewModelProvider(getActivity()).get(GameViewModel.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.teamView);
        if(dashboardRVAdapter == null)
            dashboardRVAdapter = new DashboardRVAdapter(getContext());
        if(recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(dashboardRVAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        if(gameViewModel.getPlayersByUserTeam(gameViewModel.getAllUsers().getValue().get(0).name) != null) {
            dashboardRVAdapter.setRegular(gameViewModel.getPlayersByUserTeam(gameViewModel.getAllUsers()
                    .getValue().get(0).name).stream().map(x -> x.role).collect(Collectors.toList()));
            dashboardRVAdapter.setNames(gameViewModel.getPlayersByUserTeam(gameViewModel.getAllUsers()
                    .getValue().get(0).name).stream().map(x -> x.name).collect(Collectors.toList()));
            dashboardRVAdapter.setSquadNames(gameViewModel.getPlayersByUserTeam(gameViewModel.getAllUsers()
                    .getValue().get(0).name).stream().map(x -> x.squad).collect(Collectors.toList()));
        }

        LinearLayout buttonsList = getActivity().findViewById(R.id.dashboardTeams);

        for(int i = 0; i < gameViewModel.getAllUsers().getValue().size(); i++){
            Button newButton = new Button(getActivity());
            newButton.setText(gameViewModel.getAllUsers().getValue().get(i).name);
            newButton.setBackgroundResource(R.drawable.rect);
            newButton.setOnClickListener((e) -> {
                if(gameViewModel.getPlayersByUserTeam(newButton.getText().toString()) != null) {
                    dashboardRVAdapter = new DashboardRVAdapter(getActivity());
                    dashboardRVAdapter.setRegular(gameViewModel.getPlayersByUserTeam(newButton.getText().toString())
                            .stream().map(x -> x.role).collect(Collectors.toList()));
                    dashboardRVAdapter.setNames(gameViewModel.getPlayersByUserTeam(newButton.getText().toString())
                            .stream().map(x -> x.name).collect(Collectors.toList()));
                    dashboardRVAdapter.setSquadNames(gameViewModel.getPlayersByUserTeam(newButton.getText().toString())
                            .stream().map(x -> x.squad).collect(Collectors.toList()));
                    recyclerView.setAdapter(dashboardRVAdapter);
                }
            });
            buttonsList.addView(newButton);
        }

    }
}