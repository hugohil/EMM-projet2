package com.example.clement.emm_project2.app.drawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private ArrayList<DrawerItem> drawerItems;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        this.drawerItems = new ArrayList<DrawerItem>();
        this.drawerListView = (ListView)findViewById(R.id.left_drawer);
        this.drawerAdapter = new DrawerAdapter(this, drawerItems);
        this.drawerListView.setAdapter(drawerAdapter);
        this.drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        // Add drawer toggle button here
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open, // Menu
                R.string.drawer_close
        ) {
            // Save precedent Title if we need to use it later
            public String drawerTitle = getSupportActionBar().getTitle().toString();

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.drawer_menu);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            if ( this.drawerLayout.isDrawerOpen(this.drawerListView)) {
                this.drawerLayout.closeDrawer(this.drawerListView);
            }
            else {
                this.drawerLayout.openDrawer(this.drawerListView);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void setDrawerContent(ArrayList<DrawerItem> items) {
        Log.d(TAG, "Setting drawer content to " + items.size() + " items !");
        this.drawerItems.clear();
        this.drawerItems.addAll(items);
        this.drawerAdapter.notifyDataSetChanged();
    }

    protected abstract int getLayoutResourceId();

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void selectItem(int position) {
        DrawerItem selectedItem = drawerItems.get(position);

        this.onNavItemSelected(selectedItem.getId());
        drawerListView.setItemChecked(position, true);

        if ( selectedItem.updateActionBarTitle()) {
            setTitle(selectedItem.getLabel());
        }

        if ( this.drawerLayout.isDrawerOpen(this.drawerListView)) {
            drawerLayout.closeDrawer(drawerListView);
        }
    }

    protected abstract void onNavItemSelected(int id);

}
