package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.SubCatActivity;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.SubCategory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * Created by perso on 13/08/15.
 */
public class SubCatListAdapter extends ArrayAdapter<SubCategory> {
    private static class ViewHolder{
        TextView title;
        RelativeLayout layout;
    }

    public SubCatListAdapter(Context context, ArrayList<SubCategory> subcatList) {
        super(context, 0, subcatList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.subcategory_row, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.subcat_row_title);
            viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.subcat_row_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final SubCategory subcat = getItem(position);

        viewHolder.title.setText(subcat.getTitle());

        return convertView;
    }
}
