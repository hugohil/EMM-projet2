package com.example.clement.emm_project2.app.drawer;

/**
 * Created by Clement on 14/08/15.
 */
public class DrawerSectionItem implements DrawerItem {

    public static final int ITEM_TYPE = 1 ;

    private int id ;
    private String label ;
    private boolean updateActionBarTitle ;

    private DrawerSectionItem() {
    }

    public static DrawerSectionItem create( int id, String label, boolean updateActionBarTitle ) {
        DrawerSectionItem item = new DrawerSectionItem();
        item.setId(id);
        item.setLabel(label);
        item.setUpdateActionBarTitle(updateActionBarTitle);
        return item;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public int getType() {
        return ITEM_TYPE;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public boolean isUpdateActionBarTitle() {
        return updateActionBarTitle;
    }

    public void setUpdateActionBarTitle(boolean updateActionBarTitle) {
        this.updateActionBarTitle = updateActionBarTitle;
    }
}
