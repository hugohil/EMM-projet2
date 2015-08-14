package com.example.clement.emm_project2.app.drawer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.DrawerAdapter;

import java.util.ArrayList;

/**
 * Created by Clement on 14/08/15.
 * This abstract class must be extended by Activities needing the drawer
 */
public abstract class DrawerActivity extends ActionBarActivity {

    private final static String TAG = DrawerActivity.class.getSimpleName();

    private ListView drawerListView;
    private DrawerAdapter drawerAdapter;
    private ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        this.drawerListView = (ListView)findViewById(R.id.left_drawer);
        drawerAdapter = new DrawerAdapter(this, R.layout.drawer_item, drawerItems);
        this.drawerListView.setAdapter(drawerAdapter);
    }

    protected void setDrawerContent(ArrayList<DrawerItem> items) {
        Log.d(TAG, "Setting drawer content to " + items.size() + " items !");
        this.drawerAdapter.clear();
        this.drawerAdapter.addAll(items);
        this.drawerAdapter.notifyDataSetChanged();
    }

    protected abstract int getLayoutResourceId();

}
