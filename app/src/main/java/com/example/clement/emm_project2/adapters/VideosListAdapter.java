package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.activities.FormationDetailActivity;
import com.example.clement.emm_project2.activities.StartedVideosActivity;
import com.example.clement.emm_project2.activities.SubCatActivity;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by perso on 07/09/15.
 */
public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.ViewHolder> {
    private static final String TAG = VideosListAdapter.class.getSimpleName();
    private List<Formation> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.videos_row_title);
            Log.d(TAG, title.toString());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public VideosListAdapter(List<Formation> myDataset) {
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
        Log.d(TAG, holder.title.toString());
        final Formation formation = mDataset.get(position);
        holder.title.setText(formation.getTitle());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.getAppContext(), FormationDetailActivity.class);
                intent.putExtra("ean", formation.getEan());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                App.getAppContext().startActivity(intent);
            }
        });

        holder.title.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(App.getAppContext(), R.style.alertDialogStyle);
                builder.setTitle(R.string.remove_video_title);
                builder.setMessage(R.string.remove_videos_from_path);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPrefUtil.unregisterPendingFormation(formation);
                            Intent i = new Intent(App.getAppContext(), StartedVideosActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            App.getAppContext().startActivity(i);
                        }
                    }
                );
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;
                            }
                        }
                );
                AlertDialog dialog = builder.create();
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

                dialog.show();
                return true;
                }
            }

            );

        }

                // Return the size of your dataset (invoked by the layout manager)
        @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
