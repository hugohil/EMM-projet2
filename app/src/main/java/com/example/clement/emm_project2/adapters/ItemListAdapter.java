package com.example.clement.emm_project2.adapters;

/**
 * Created by Clement on 06/09/15.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.model.Item;

public class ItemListAdapter extends BaseExpandableListAdapter {

    private Context _context;

    private List<Item> chapters;
    private HashMap<Item,List<Item>> videos;

    public ItemListAdapter(Context context, List<Item> listDataHeader,
                           HashMap<Item, List<Item>> listChildData) {
        this._context = context;
        this.chapters = listDataHeader;
        this.videos = listChildData;
    }

    @Override
    public Item getChild(int groupPosition, int childPosititon) {
        return this.videos.get(chapters.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getTitle();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_video_row, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.videos.get(this.chapters.get(groupPosition))
                .size();
    }

    @Override
    public Item getGroup(int groupPosition) {
        return this.chapters.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.chapters.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getTitle() + " ("+ getGroup(groupPosition).getChildrens().size() + " videos)";
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_chapter_row, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}