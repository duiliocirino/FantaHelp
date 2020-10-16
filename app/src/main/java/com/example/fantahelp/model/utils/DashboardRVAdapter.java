package com.example.fantahelp.model.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fantahelp.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardRVAdapter extends RecyclerView.Adapter<DashboardRVAdapter.ViewHolder>{

    private List<String> roles = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<String> squadNames = new ArrayList<>();
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView roleView;
        public TextView nameView;
        public TextView squadView;
        View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            roleView = v.findViewById(R.id.roleTeamView);
            nameView = v.findViewById(R.id.playerNameTeamView);
            squadView = v.findViewById(R.id.squadTeamView);
        }
    }

    public void setNames(List<String> names) {
        this.names.addAll(names);
        notifyDataSetChanged();
    }

    public void setSquadNames(List<String> squadNames) {
        this.squadNames.addAll(squadNames);
        notifyDataSetChanged();
    }

    public void setRegular(List<String> roles) {
        this.roles.addAll(roles);
        notifyDataSetChanged();

    }

    public DashboardRVAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DashboardRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.dashboard_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        DashboardRVAdapter.ViewHolder vh = new DashboardRVAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DashboardRVAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String role = roles.get(position);
        final String name = names.get(position);
        final String squadName = squadNames.get(position);
        holder.roleView.setText(role);
        holder.nameView.setText(name);
        holder.squadView.setText(squadName);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(names != null)
            return names.size();
        return 0;
    }

}
