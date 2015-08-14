package com.example.clement.emm_project2.adapters.drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clement.emm_project2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 13/08/15.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_ICON = 1;
    private static final int TYPE_ITEM_SIMPLE = 2;

    private ArrayList<String> mNavTitles;
    private int mIcons[];


    public DrawerAdapter(ArrayList<String> titles ,int icons[], Context context) {
        mNavTitles = titles;
        mIcons = icons;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        TextView textView;
        ImageView imageView;
        Context context;

        public ViewHolder(View itemView, int ViewType, Context c) {
            super(itemView);
            context = c;
            itemView.setClickable(true);

            switch(ViewType) {
                case TYPE_ITEM_ICON:
                    textView = (TextView) itemView.findViewById(R.id.rowText);
                    imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                    Holderid = 1;
                    break;
                case TYPE_ITEM_SIMPLE:
                    Holderid = 2;
                    textView = (TextView) itemView.findViewById(R.id.rowSimple);
                    break;
                case TYPE_HEADER :
                    Holderid = 0;
                    break;
            }
        }
    }

    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        switch(viewType) {
            case TYPE_ITEM_ICON:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_icon_row,parent,false);
                holder = new ViewHolder(view, viewType, context);
                break;
            case TYPE_ITEM_SIMPLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_simple_row,parent,false);
                holder = new ViewHolder(view, viewType, context);
                break;
            case TYPE_HEADER :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header,parent,false);
                holder = new ViewHolder(view, viewType, context);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        switch(holder.Holderid) {
            case 0: //Header
                break;
            case 1: //Item icon
                holder.textView.setText(mNavTitles.get(position - 1));
                holder.imageView.setImageResource(mIcons[position -1]);
                break;
            case 2: //Item simple
                holder.textView.setText(mNavTitles.get(position - 1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (isPositionHeader(position))
            return TYPE_HEADER;
        if (hasAssociatedIcon(position))
            return TYPE_ITEM_ICON;
        else
            return TYPE_ITEM_SIMPLE;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean hasAssociatedIcon(int position) {
        if(position > mIcons.length)
            return false;
        return true;
    }
}
