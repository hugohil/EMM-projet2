package com.example.clement.emm_project2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.app.drawer.DrawerItem;
import com.example.clement.emm_project2.app.drawer.DrawerSectionItem;
import com.example.clement.emm_project2.app.drawer.DrawerSection;

import java.util.ArrayList;

/**
 * Created by Clement on 14/08/15.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {

    private LayoutInflater inflater;

    public DrawerAdapter(Context context, int textViewResourceId, ArrayList<DrawerItem> items) {
        super(context, textViewResourceId, items);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null ;
        DrawerItem menuItem = this.getItem(position);
        if ( menuItem.getType() == DrawerSectionItem.ITEM_TYPE ) {
            view = getItemView(convertView, parent, menuItem );
        }
        else {
            view = getSectionView(convertView, parent, menuItem);
        }
        return view ;
    }

    public View getItemView( View convertView, ViewGroup parentView, DrawerItem drawerItem) {

        DrawerSectionItem drawerSectionItem = (DrawerSectionItem) drawerItem;
        NavMenuItemHolder navMenuItemHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate( R.layout.drawer_item, parentView, false);
            TextView labelView = (TextView) convertView
                    .findViewById( R.id.navmenuitem_label );

            navMenuItemHolder = new NavMenuItemHolder();
            navMenuItemHolder.labelView = labelView ;

            convertView.setTag(navMenuItemHolder);
        }

        if ( navMenuItemHolder == null ) {
            navMenuItemHolder = (NavMenuItemHolder) convertView.getTag();
        }

        navMenuItemHolder.labelView.setText(drawerSectionItem.getLabel());

        return convertView ;
    }

    public View getSectionView(View convertView, ViewGroup parentView,
                               DrawerItem drawerItem) {

        DrawerSection drawerSection = (DrawerSection) drawerItem;
        NavMenuSectionHolder navMenuItemHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate( R.layout.drawer_section, parentView, false);
            TextView labelView = (TextView) convertView
                    .findViewById( R.id.navmenusection_label );

            ImageView iconView = (ImageView) convertView
                    .findViewById( R.id.navmenuitem_icon );

            navMenuItemHolder = new NavMenuSectionHolder();
            navMenuItemHolder.labelView = labelView ;
            navMenuItemHolder.iconView = iconView ;
            convertView.setTag(navMenuItemHolder);
        }

        if ( navMenuItemHolder == null ) {
            navMenuItemHolder = (NavMenuSectionHolder) convertView.getTag();
        }

        navMenuItemHolder.labelView.setText(drawerSection.getLabel());
        navMenuItemHolder.iconView.setImageResource(drawerSection.getIcon());

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


    private static class NavMenuItemHolder {
        private TextView labelView;
    }

    private class NavMenuSectionHolder {
        private TextView labelView;
        private ImageView iconView;
    }
}
