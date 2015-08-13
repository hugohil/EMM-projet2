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

    private final static String TAG = CatListAdapter.class.getSimpleName();

    public CatListAdapter(Context context, ArrayList<Category> catList) {
        super(context, 0, catList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_row, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.cat_row_title);
        title.setText(getItem(position).getTitle());

        return convertView;
    }
}
