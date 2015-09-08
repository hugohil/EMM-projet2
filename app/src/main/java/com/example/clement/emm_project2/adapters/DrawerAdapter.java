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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 14/08/15.
 */
public class DrawerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DrawerItem> items;
    private Activity context;

    private Map<String, Integer> categorieIcons;

    public DrawerAdapter(Activity context, ArrayList<DrawerItem> items) {
        this.context = context;
        this.items = items;

        categorieIcons = new HashMap<String, Integer>();
        categorieIcons.put("3D", R.drawable.ic_action_3d_rotation);
        categorieIcons.put("Audio-MAO", R.drawable.ic_action_speaker );
        categorieIcons.put("Bureautique", R.drawable.ic_action_desktop_mac);
        categorieIcons.put("Business", R.drawable.ic_action_work);
        categorieIcons.put("Code", R.drawable.ic_action_code);
        categorieIcons.put("Infographie", R.drawable.ic_action_blur_on);
        categorieIcons.put("Informatique", R.drawable.ic_action_keyboard);
        categorieIcons.put("Photographie", R.drawable.ic_action_camera);
        categorieIcons.put("Vidéo-Compositing", R.drawable.ic_action_movie);
        categorieIcons.put("Web-Multimédia", R.drawable.ic_action_public);
    }

    @Override
    public int getCount() {
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
            itemHolder.iconView = (ImageView)convertView.findViewById(R.id.navmenuitem_icon);

            convertView.setTag(itemHolder);
        }

        if ( itemHolder == null ) {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        itemHolder.labelView.setText(drawerSectionItem.getLabel());
        Log.d("OULAL", drawerSectionItem.getLabel());
        if(drawerSectionItem.getLabel().equals("Favoris")) {
            itemHolder.iconView.setImageResource(R.drawable.ic_action_loyalty);
        } else if(drawerSectionItem.getLabel().equals("Parcours")) {
            itemHolder.iconView.setImageResource(R.drawable.ic_action_book);
        } else {
            itemHolder.iconView.setImageResource(categorieIcons.get(drawerSectionItem.getLabel()));
        }

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
        private ImageView iconView;
    }

    private class SectionHolder {
        private TextView labelView;
        private ImageView iconView;
    }
}
