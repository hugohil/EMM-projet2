package com.example.clement.emm_project2.app.drawer;

import android.content.Context;

/**
 * Created by Clement on 14/08/15.
 */
public class DrawerSection implements DrawerItem {

    public static final int SECTION_TYPE = 0;
    private int id;
    private String label;
    private int icon ;

    private DrawerSection() {
    }

    public static DrawerSection create( int id, String label, String icon, Context context ) {
        DrawerSection section = new DrawerSection();
        section.setLabel(label);
        section.setIcon(context.getResources().getIdentifier( icon, "drawable", context.getPackageName()));
        return section;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int getType() {
        return SECTION_TYPE;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean updateActionBarTitle() {
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
