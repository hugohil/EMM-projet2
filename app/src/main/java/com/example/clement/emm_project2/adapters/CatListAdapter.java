package com.example.clement.emm_project2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.model.Category;

import java.util.ArrayList;

/**
 * Created by perso on 11/08/15.
 */
public class CatListAdapter extends ArrayAdapter<Category> {
    private static class ViewHolder{
        TextView title;
    }

    public CatListAdapter(Context context, ArrayList<Category> catList) {
        super(context, 0, catList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_row, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.cat_row_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(getItem(position).getTitle());

        return convertView;
    }
}
