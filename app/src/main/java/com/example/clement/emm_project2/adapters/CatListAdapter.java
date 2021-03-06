package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.activities.SubCatActivity;
import com.example.clement.emm_project2.model.Category;

import java.util.ArrayList;

/**
 * Created by perso on 11/08/15.
 */
public class CatListAdapter extends ArrayAdapter<Category> {
    private static class ViewHolder{
        TextView title;
        TextView subCatNumber;
        RelativeLayout layout;
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
            viewHolder.subCatNumber = (TextView) convertView.findViewById(R.id.cat_row_subcat_number);
            viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.cat_row_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Category cat = getItem(position);

        viewHolder.title.setText(cat.getTitle());
        viewHolder.subCatNumber.setText(cat.getSubCategoriesAsList().size() + "");
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = (Activity) getContext();
                Intent i = new Intent(act, SubCatActivity.class);
                i.putExtra("desc", cat.getDescription());
                i.putExtra("title", cat.getTitle());
                i.putExtra("catId", cat.getMongoID());
                act.startActivity(i);
                // call finish() current activity loads the next one much faster but disable the back button
                // act.finish();
            }
        });

        return convertView;
    }
}
