package com.example.clement.emm_project2.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.util.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Clement on 19/08/15.
 */
public class FormationListAdapter extends RecyclerView.Adapter<FormationListAdapter
        .FormationHolder> {

    private final static String TAG = FormationListAdapter.class.getSimpleName();
    private ArrayList<Formation> formations;
    private static FormationClickListener cardClickListener;

    public static class FormationHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView formationName;
        TextView objectives;
        ImageView formationPoster;

        public FormationHolder(View itemView) {
            super(itemView);
            formationName = (TextView) itemView.findViewById(R.id.formation_card_title);
            objectives = (TextView) itemView.findViewById(R.id.formation_card_objectives);
            formationPoster = (ImageView) itemView.findViewById(R.id.formationImage);
            Log.i(TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cardClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public FormationListAdapter(ArrayList<Formation> formations) {
        this.formations = formations;
    }

    public void setOnCardClickListener(FormationClickListener clickListener) {
        this.cardClickListener = clickListener;
    }

    @Override
    public FormationHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.formation_card, parent, false);

        FormationHolder dataObjectHolder = new FormationHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(FormationHolder holder, int position) {
        Formation formation = formations.get(position);
        holder.formationName.setText(formation.getTitle());

        assert formation.getObjectives() != null;
        if(!formation.getObjectives().equals("null")){
            Log.d(TAG, "obj: " + formation.getObjectives());
            holder.objectives.setText(Html.fromHtml(formation.getObjectives()));
        }

        // Loader image - will be shown before loading image
        int loader = R.drawable.loader; // Change this to loader img :)

        ImageView imageView = holder.formationPoster;
        ImageLoader imgLoader = new ImageLoader(App.getAppContext());
        imgLoader.DisplayImage(formation.getPoster(), loader, imageView);
    }

    public void addItem(Formation formation, int index) {
        formations.add(index, formation);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        formations.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return formations.size();
    }

    public String getFormationEan(int position) {
        return formations.get(position).getEan();
    }

    public interface FormationClickListener {
        void onItemClick(int position, View v);
    }
}
