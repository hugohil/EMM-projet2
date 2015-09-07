package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.activities.SubCatActivity;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by perso on 07/09/15.
 */
public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.ViewHolder> {
    private List<Item> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public VideosListAdapter(List<Item> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VideosListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
