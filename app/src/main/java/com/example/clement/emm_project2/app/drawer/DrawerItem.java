package com.example.clement.emm_project2.app.drawer;

/**
 * Created by Clement on 14/08/15.
 */
public interface DrawerItem {
    public int getId();
    public String getLabel();
    public int getType();
    public boolean isEnabled();
    public boolean updateActionBarTitle();
}
