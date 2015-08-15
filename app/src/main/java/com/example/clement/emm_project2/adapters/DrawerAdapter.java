package com.example.clement.emm_project2.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.app.drawer.DrawerItem;
import com.example.clement.emm_project2.app.drawer.DrawerSection;
import com.example.clement.emm_project2.app.drawer.DrawerSectionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 14/08/15.
 */
public class DrawerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DrawerItem> items;
    private Activity context;

    public DrawerAdapter(Activity context, ArrayList<DrawerItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        Log.d(DrawerAdapter.class.getSimpleName(), "Count returned by getCount => "+items.size());
        return items.size();
    }

    @Override
    public DrawerItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Maybe we could return something else there...
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        DrawerItem menuItem = getItem(position);

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if ( menuItem.getType() == DrawerSectionItem.ITEM_TYPE ) {
            view = getItemView(convertView, parent, menuItem );
        }
        else {
            view = getSectionView(convertView, parent, menuItem);
        }
        Log.d(DrawerAdapter.class.getSimpleName(), "View returned by getView => "+view);
        return view ;
    }

    public View getItemView( View convertView, ViewGroup parentView, DrawerItem drawerItem) {

        DrawerSectionItem drawerSectionItem = (DrawerSectionItem) drawerItem;
        ItemHolder itemHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_item, parentView, false);
            TextView labelView = (TextView) convertView
                    .findViewById( R.id.navmenuitem_label );

            itemHolder = new ItemHolder();
            itemHolder.labelView = labelView ;

            convertView.setTag(itemHolder);
        }

        if ( itemHolder == null ) {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        itemHolder.labelView.setText(drawerSectionItem.getLabel());

        return convertView ;
    }

    public View getSectionView(View convertView, ViewGroup parentView,
                               DrawerItem drawerItem) {

        DrawerSection drawerSection = (DrawerSection) drawerItem;
        SectionHolder sectionHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate( R.layout.drawer_section, parentView, false);
            TextView labelView = (TextView) convertView
                    .findViewById( R.id.navmenusection_label );

            ImageView iconView = (ImageView) convertView
                    .findViewById( R.id.navmenuitem_icon );

            sectionHolder = new SectionHolder();
            sectionHolder.labelView = labelView ;
            sectionHolder.iconView = iconView ;
            convertView.setTag(sectionHolder);
        }

        if ( sectionHolder == null ) {
            sectionHolder = (SectionHolder) convertView.getTag();
        }

        sectionHolder.labelView.setText(drawerSection.getLabel());
        sectionHolder.iconView.setImageResource(drawerSection.getIcon());

        return convertView ;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return this.getItem(position).getType();
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).isEnabled();
    }

    private static class ItemHolder {
        private TextView labelView;
    }

    private class SectionHolder {
        private TextView labelView;
        private ImageView iconView;
    }
}
