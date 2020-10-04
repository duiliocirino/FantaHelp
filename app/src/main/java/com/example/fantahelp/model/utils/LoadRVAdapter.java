package com.example.fantahelp.model.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fantahelp.R;
import com.example.fantahelp.view.GameActivity;

import java.util.ArrayList;
import java.util.List;

public class LoadRVAdapter extends RecyclerView.Adapter<LoadRVAdapter.ViewHolder> {

    private List<String> values = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public Button loadButton;
        View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            textView = v.findViewById(R.id.gameInBox);
            loadButton = v.findViewById(R.id.loadButton);
        }
    }

    public void setValues(List<String> values) {
        if(values != null) {
            this.values.addAll(values);
            notifyDataSetChanged();
        }
    }

    public void setIds(List<Integer> ids) {
        if(ids != null){
            this.ids.addAll(ids);
            notifyDataSetChanged();
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LoadRVAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LoadRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.load_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        holder.textView.setText(name);
        holder.loadButton.setOnClickListener( (l) -> {
            Intent intent = new Intent(context, GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("gameId", ids.get(position));
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(values != null)
            return values.size();
        return 0;
    }

}
