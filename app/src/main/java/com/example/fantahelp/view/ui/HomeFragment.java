package com.example.fantahelp.view.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fantahelp.R;
import com.example.fantahelp.model.entities.Player;
import com.example.fantahelp.model.entities.Team;
import com.example.fantahelp.model.utils.CreditsCalculator;
import com.example.fantahelp.model.utils.DecisionMaker;
import com.example.fantahelp.model.utils.GameRVAdapter;
import com.example.fantahelp.model.utils.ValueCalculator;
import com.example.fantahelp.viewModel.GameViewModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GameViewModel gameViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        gameViewModel = new ViewModelProvider(getActivity()).get(GameViewModel.class);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setButtons();
    }

    private void setButtons(){
        AutoCompleteTextView searchBar = getActivity().findViewById(R.id.playerSearchBar);
        EditText editText = getActivity().findViewById(R.id.editTextBetNumber);
        Spinner spinner = getActivity().findViewById(R.id.spinner);

        // MATH BUTTONS


        Button button = getActivity().findViewById(R.id.buttonPlus5);
        button.setOnClickListener((e) -> {
            int bet = Integer.parseInt(editText.getText().toString());
            editText.setText(String.valueOf(bet + 5));
        });
        button = getActivity().findViewById(R.id.buttonPlus1);
        button.setOnClickListener((e) -> {
            int bet = Integer.parseInt(editText.getText().toString());
            editText.setText(String.valueOf(bet + 1));
        });
        button = getActivity().findViewById(R.id.buttonMinus1);
        button.setOnClickListener((e) -> {
            int bet = Integer.parseInt(editText.getText().toString());
            if(bet - 1 > 0) {
                editText.setText(String.valueOf(bet - 1));
            }
            else {
                Toast.makeText(getActivity(), R.string.negativeNum, 1).show();
            }
        });
        button = getActivity().findViewById(R.id.buttonMinus5);
        button.setOnClickListener((e) -> {
            int bet = Integer.parseInt(editText.getText().toString());
            if(bet - 5 > 0) {
                editText.setText(String.valueOf(bet - 5));
            }
            else {
                Toast.makeText(getActivity(), R.string.negativeNum, 1).show();
            }
        });

        //UP ROLE BUTTONS


        button = getActivity().findViewById(R.id.keeperButton);
        Button finalButton = button;
        button.setOnClickListener((e) -> {
            TextView textView = getActivity().findViewById(R.id.roleTextView);

            textView.setText(finalButton.getText());
            gameViewModel.setSelectedRole(finalButton.getText().toString());

            RecyclerView recyclerView = getActivity().findViewById(R.id.suggestionBox);
            GameRVAdapter gameRVAdapter = (GameRVAdapter) recyclerView.getAdapter();
            Map<Integer, Integer> values = ValueCalculator.getValues(getActivity().getApplication(), gameViewModel.getAllPlayers().getValue(), gameViewModel.getSelectedRole());
            setAdapter(gameRVAdapter, gameViewModel.getAllPlayers().getValue().stream().filter(x -> x.role.equals(gameViewModel.getSelectedRole())).sorted((a, b) -> values.get(b.id) - values.get(a.id)).collect(Collectors.toList()), recyclerView);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_list_item_1);

            final GameRVAdapter[] mAdapter = {new GameRVAdapter(getContext())};


            updateCreditsViews(gameViewModel.getAllTeams().getValue());
            updateSearchBar(gameViewModel.getAllPlayers().getValue(), mAdapter, arrayAdapter);
        });

        button = getActivity().findViewById(R.id.defenderButton);
        Button finalButton1 = button;
        button.setOnClickListener((e) -> {
            TextView textView = getActivity().findViewById(R.id.roleTextView);
            textView.setText(finalButton1.getText());
            gameViewModel.setSelectedRole(finalButton1.getText().toString());

            RecyclerView recyclerView = getActivity().findViewById(R.id.suggestionBox);
            GameRVAdapter gameRVAdapter = (GameRVAdapter) recyclerView.getAdapter();
            Map<Integer, Integer> values = ValueCalculator.getValues(getActivity().getApplication(), gameViewModel.getAllPlayers().getValue(), gameViewModel.getSelectedRole());
            setAdapter(gameRVAdapter, gameViewModel.getAllPlayers().getValue().stream().filter(x -> x.role.equals(gameViewModel.getSelectedRole())).sorted((a, b) -> values.get(b.id) - values.get(a.id)).collect(Collectors.toList()), recyclerView);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_list_item_1);

            final GameRVAdapter[] mAdapter = {new GameRVAdapter(getContext())};

            updateCreditsViews(gameViewModel.getAllTeams().getValue());
            updateSearchBar(gameViewModel.getAllPlayers().getValue(), mAdapter, arrayAdapter);
        });

        button = getActivity().findViewById(R.id.midfielderButton);
        Button finalButton2 = button;
        button.setOnClickListener((e) -> {
            TextView textView = getActivity().findViewById(R.id.roleTextView);
            textView.setText(finalButton2.getText());
            gameViewModel.setSelectedRole(finalButton2.getText().toString());

            RecyclerView recyclerView = getActivity().findViewById(R.id.suggestionBox);
            GameRVAdapter gameRVAdapter = (GameRVAdapter) recyclerView.getAdapter();
            Map<Integer, Integer> values = ValueCalculator.getValues(getActivity().getApplication(), gameViewModel.getAllPlayers().getValue(), gameViewModel.getSelectedRole());
            setAdapter(gameRVAdapter, gameViewModel.getAllPlayers().getValue().stream().filter(x -> x.role.equals(gameViewModel.getSelectedRole())).sorted((a, b) -> values.get(b.id) - values.get(a.id)).collect(Collectors.toList()), recyclerView);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_list_item_1);

            final GameRVAdapter[] mAdapter = {new GameRVAdapter(getContext())};

            updateCreditsViews(gameViewModel.getAllTeams().getValue());
            updateSearchBar(gameViewModel.getAllPlayers().getValue(), mAdapter, arrayAdapter);
        });
        button = getActivity().findViewById(R.id.attackerButton);
        Button finalButton3 = button;
        button.setOnClickListener((e) -> {
            TextView textView = getActivity().findViewById(R.id.roleTextView);
            textView.setText(finalButton3.getText());
            gameViewModel.setSelectedRole(finalButton3.getText().toString());

            RecyclerView recyclerView = getActivity().findViewById(R.id.suggestionBox);
            GameRVAdapter gameRVAdapter = (GameRVAdapter) recyclerView.getAdapter();
            Map<Integer, Integer> values = ValueCalculator.getValues(getActivity().getApplication(), gameViewModel.getAllPlayers().getValue(), gameViewModel.getSelectedRole());
            setAdapter(gameRVAdapter, gameViewModel.getAllPlayers().getValue().stream().filter(x -> x.role.equals(gameViewModel.getSelectedRole())).sorted((a, b) -> values.get(b.id) - values.get(a.id)).collect(Collectors.toList()), recyclerView);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_list_item_1);

            final GameRVAdapter[] mAdapter = {new GameRVAdapter(getContext())};

            updateCreditsViews(gameViewModel.getAllTeams().getValue());
            updateSearchBar(gameViewModel.getAllPlayers().getValue(), mAdapter, arrayAdapter);
        });


        //AUCTION BUTTONS

        button = getActivity().findViewById(R.id.assignButton);
        button.setOnClickListener((e) -> {
            String playerName = searchBar.getText().toString();
            int bet = Integer.parseInt(editText.getText().toString());
            if(playerName.isEmpty()) return;
            if(gameViewModel.assignPlayer(spinner.getSelectedItem().toString(), playerName, bet, false)){
                updateCreditsViews(gameViewModel.getAllTeams().getValue());
                Toast.makeText(getActivity(), "Player correctly assigned", 1).show();
            } else Toast.makeText(getActivity(), "You can't assign this player to this user", 1).show();
        });

        searchBar.setOnItemClickListener((parent, view, position, id) -> decisionViewsHandler(searchBar));

        EditText editTextNumber = getActivity().findViewById(R.id.editTextBetNumber);
        editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                decisionViewsHandler(searchBar);
            }
        });
        editTextNumber.setOnEditorActionListener((v, actionId, event) -> {
            decisionViewsHandler(searchBar);
            return true;
        });
    }

    public void setAdapter(GameRVAdapter mAdapter, List<Player> playersRole, RecyclerView recyclerView){
        mAdapter = new GameRVAdapter(getContext());
        if(playersRole != null) {
            mAdapter.setNames(playersRole.stream().map(x -> x.name).collect(Collectors.toList()));
            mAdapter.setSquadNames(playersRole.stream().map(x -> x.squad).collect(Collectors.toList()));
            mAdapter.setRegular(playersRole.stream().map(x -> x.regularness).collect(Collectors.toList()));
            mAdapter.setMyVote(playersRole.stream().map(x -> x.myRating).collect(Collectors.toList()));
            mAdapter.setValues(ValueCalculator.getValues(getActivity().getApplication(), playersRole, gameViewModel.getSelectedRole()).values().stream().sorted((a, b) -> b - a).collect(Collectors.toList()));
            mAdapter.setPrices(playersRole.stream().map(x -> x.expPrice).collect(Collectors.toList()));
            // Check if in a team
            mAdapter.setVisibility(playersRole.stream().map(x -> x.role.equals(gameViewModel.getSelectedRole()) && gameViewModel.playerNotInTeam(x)).collect(Collectors.toList()));
        }
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //ValueCalculator.updateValues(requireActivity().getApplication(), gameViewModel.getAllPlayers().getValue(), gameViewModel.getSelectedRole());
        //Spinner of users


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item);

        //SearchBar

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_list_item_1);

        final GameRVAdapter[] mAdapter = {new GameRVAdapter(getContext())};

        gameViewModel.getAllPlayers().observe(getViewLifecycleOwner(), players -> {
            updateSearchBar(players, mAdapter, arrayAdapter);
        });
        gameViewModel.getGame().observe(getViewLifecycleOwner(), game -> {

        });
        gameViewModel.getAllUsers().observe(getViewLifecycleOwner(), users -> {
            Spinner spinner = getActivity().findViewById(R.id.spinner);
            adapter.addAll(users.stream()
                    .map(x -> x.name)
                    .toArray(String[]::new));
            spinner.setAdapter(adapter);
            gameViewModel.retrieveAllTeams(users.stream().map(x -> x.id).collect(Collectors.toList()));
            if(gameViewModel.getMyUserId() == -1) {
                gameViewModel.setMyUserId(users.get(0).id);
                gameViewModel.setMyTeamId(users.get(0).team_id);
            }
            gameViewModel.getAllTeams().observe(getViewLifecycleOwner(), teams -> {
                if(teams != null) {
                    updateCreditsViews(teams);
                }
            });
        });

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void updateSearchBar(List<Player> players, GameRVAdapter[] mAdapter, ArrayAdapter<String> arrayAdapter) {
        if(players != null) {
            Map<Integer, Integer> values = ValueCalculator.getValues(getActivity().getApplication(), players, gameViewModel.getSelectedRole());
            List<Player> playersRole = players.stream().filter(x -> x.role.equals(gameViewModel.getSelectedRole())).sorted((a, b) -> values.get(b.id) - values.get(a.id)).collect(Collectors.toList());

            RecyclerView recyclerView = getActivity().findViewById(R.id.suggestionBox);
            recyclerView.setHasFixedSize(true);

            AutoCompleteTextView searchBar = getActivity().findViewById(R.id.playerSearchBar);

            arrayAdapter.clear();
            arrayAdapter.addAll(playersRole.stream().filter(x -> gameViewModel.playerNotInTeam(x)).map(x -> x.name).toArray(String[]::new));
            searchBar.setAdapter(arrayAdapter);

            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(mAdapter[0]);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            setAdapter(mAdapter[0], playersRole, recyclerView);
        }
    }

    private void updateCreditsViews(List<Team> teams) {
        TextView totalCredits = getActivity().findViewById(R.id.totalCreditsNumber);
        TextView partialCredits = getActivity().findViewById(R.id.partialCreditsNum);

        totalCredits.setText(String.valueOf(gameViewModel.getMyTeam(teams).credits));
        CreditsCalculator.update(gameViewModel.getMyTeam(teams));
        partialCredits.setText(String.valueOf(CreditsCalculator.getPartialTotal(gameViewModel.getSelectedRole()))
                + "/" + String.valueOf(CreditsCalculator.getPartialTotal(gameViewModel.getSelectedRole())));
    }

    private void decisionViewsHandler(AutoCompleteTextView searchBar) {
        if(searchBar.getText().toString().equals("")) return;
        String playerName = searchBar.getText().toString();
        Player player = gameViewModel.getPlayerByName(playerName);
        if(player == null) return;
        EditText editTextNumber = getActivity().findViewById(R.id.editTextBetNumber);
        TextView decisionView = getActivity().findViewById(R.id.decisionView);
        TextView myVoteView = getActivity().findViewById(R.id.myVoteView);
        TextView valueView = getActivity().findViewById(R.id.valueView);
        int suggestedValue = ValueCalculator.getValue(player, getActivity().getApplication());
        String decision = "";
        try {
            decision = DecisionMaker.makeDecision(player, suggestedValue,
                    Integer.parseInt(editTextNumber.getText().toString()));
        } catch (NumberFormatException e){
            return;
        }
        decisionView.setText(decision);
        if(decision.equals("PASS")) decisionView.setTextColor(Color.RED);
        else decisionView.setTextColor(Color.GREEN);
        myVoteView.setText(String.valueOf(player.myRating));
        valueView.setText(String.valueOf(suggestedValue));
    }
}