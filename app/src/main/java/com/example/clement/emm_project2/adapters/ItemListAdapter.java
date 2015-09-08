package com.example.clement.emm_project2.adapters;

/**
 * Created by Clement on 06/09/15.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.util.SharedPrefUtil;

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
        Item item = getChild(groupPosition, childPosition);
        final String childText = item.getTitle();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_video_row, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        Set<String> seenItems = SharedPrefUtil.getSeenItemIds();
        if( seenItems != null && seenItems.contains(item.getMongoID())) {
            ImageView playIcon = (ImageView) convertView.findViewById(R.id.icon_additional_icon);
            playIcon.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_action_done));
        }

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

        List<String> childrenIds = getGroup(groupPosition).getChildrens();
        boolean sawAllChildrens = true;
        for(String childrenId : childrenIds) {
            if(!SharedPrefUtil.getSeenItemIds().contains(childrenId)) {
                sawAllChildrens = false;
                break;
            }
        }

        if(sawAllChildrens) {
            convertView.setBackgroundColor(Color.GRAY);
            lblListHeader.setTextColor(Color.WHITE);
        }

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