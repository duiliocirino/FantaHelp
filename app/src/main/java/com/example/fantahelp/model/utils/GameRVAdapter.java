package com.example.fantahelp.model.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fantahelp.R;
import com.example.fantahelp.view.GameActivity;

import java.util.ArrayList;
import java.util.List;

public class GameRVAdapter extends RecyclerView.Adapter<GameRVAdapter.ViewHolder>{

    private List<Boolean> visibility = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<String> squadNames = new ArrayList<>();
    private List<Integer> regularities = new ArrayList<Integer>();
    private List<Integer> myVotes = new ArrayList<>();
    private List<Integer> values = new ArrayList<>();
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameView;
        public TextView squadView;
        public TextView regularityView;
        public TextView myVoteView;
        public TextView valueView;
        View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            nameView = v.findViewById(R.id.playerNameSugg);
            squadView = v.findViewById(R.id.squadSugg);
            regularityView = v.findViewById(R.id.regularitySugg);
            myVoteView = v.findViewById(R.id.myVoteSugg);
            valueView = v.findViewById(R.id.valueSugg);
        }
    }

    public void setNames(List<String> names) {
        if(names != null) {
            this.names.addAll(names);
            notifyDataSetChanged();
        }
    }

    public void setSquadNames(List<String> squadNames) {
        if(squadNames != null) {
            this.squadNames.addAll(squadNames);
            notifyDataSetChanged();
        }
    }

    public void setRegular(List<Integer> regularities) {
        if(myVotes != null){
            this.regularities.addAll(regularities);
            notifyDataSetChanged();
        }
    }

    public void setMyVote(List<Integer> myVotes) {
        if(myVotes != null){
            this.myVotes.addAll(myVotes);
            notifyDataSetChanged();
        }
    }

    public void setValues(List<Integer> values) {
        if(values != null) {
            this.values.addAll(values);
            notifyDataSetChanged();
        }
    }

    public void setVisibility(List<Boolean> visibility){
        this.visibility = visibility;
        notifyDataSetChanged();
    }

    /*public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }*/

    // Provide a suitable constructor (depends on the kind of dataset)
    public GameRVAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GameRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.suggestion_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        GameRVAdapter.ViewHolder vh = new GameRVAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GameRVAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = names.get(position);
        final String squadName = squadNames.get(position);
        final int regularity = regularities.get(position);
        final int myVote = myVotes.get(position);
        final int value = values.get(position);
        holder.nameView.setText(name);
        holder.squadView.setText(squadName);
        holder.regularityView.setText(String.valueOf(regularity));
        holder.myVoteView.setText(String.valueOf(myVote));
        holder.valueView.setText(String.valueOf(value));
        if(visibility.get(position) == false) {
            holder.layout.setVisibility(View.GONE);
            holder.layout.setLayoutParams(new RecyclerView.LayoutParams(0 ,0));
        }
        else {
            holder.layout.setVisibility(View.VISIBLE);
            holder.layout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(values != null)
            return values.size();
        return 0;
    }
}
