package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.clement.emm_project2.activities.FormationsActivity;
import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.StringUtil;

import java.util.ArrayList;

/**
 * Created by perso on 13/08/15.
 */
public class SubCatListAdapter extends ArrayAdapter<SubCategory> {
    private static class ViewHolder{
        TextView title;
        TextView groupName = null;
    }

    public SubCatListAdapter(Context context, ArrayList<SubCategory> subcatList) {
        super(context, 0, subcatList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        final SubCategory subcat = getItem(position);
        int layoutId = R.layout.subcategory_with_separator_row;
        if(position != 0 && StringUtil.startsWithSameLetter(getItem(position - 1).getTitle(), subcat.getTitle())) {
            layoutId = R.layout.subcategory_row;
        }

        viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        viewHolder.title = (TextView) convertView.findViewById(R.id.subcat_row_title);
        viewHolder.groupName = (TextView) convertView.findViewById(R.id.subcat_group_name);

        viewHolder.title.setText(subcat.getTitle());
        if(viewHolder.groupName != null) {
            viewHolder.groupName.setText(subcat.getTitle().substring(0, 1).toUpperCase());
            View vHeader = convertView.findViewById(R.id.subcat_group_name);
            vHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do nothing here, we don't want to trigger an action when clicking on an header
                }
            });
        }



        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = (Activity) getContext();
                Intent i = new Intent(act, FormationsActivity.class);
                i.putExtra("subCatId", subcat.getMongoID());
                act.startActivity(i);
            }
        });

        return convertView;
    }
}
