package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.model.Category;

import java.util.List;

/**
 * Created by perso on 11/08/15.
 */
public class CatListAdapter extends BaseAdapter {
    private List<Category> catList;
    private LayoutInflater inflater;
    private Activity activity;

    public CatListAdapter(Activity activity, List<Category> catList) {
        this.activity = activity;
        this.catList = catList;
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int location) {
        return catList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateList(List<Category> catList) {
        this.catList.clear();
        this.catList.addAll(catList);
        this.notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        inflater = (inflater == null) ? (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) : inflater;
        convertView = (convertView == null) ? inflater.inflate(R.layout.category_row, null) : convertView;

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(catList.get(position).getTitle());

        return convertView;
    }
}
