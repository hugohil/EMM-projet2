package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.activities.SubCatActivity;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.model.SubCategory;

import java.util.ArrayList;

/**
 * Created by perso on 07/09/15.
 */
public class VideosListAdapter extends ArrayAdapter<Item> {
    private static class ViewHolder{
        TextView title;
        RelativeLayout layout;
    }

    public VideosListAdapter(Context context, ArrayList<Item> itemList) {
        super(context, 0, itemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.videos_row, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.videos_row_title);
            viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.cat_row_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Item item= getItem(position);

        viewHolder.title.setText(item.getTitle());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return convertView;
    }
}
