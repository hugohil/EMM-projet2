package com.example.clement.emm_project2.app.drawer;

import android.content.Intent;
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
import com.example.clement.emm_project2.activities.FavoriteActivity;
import com.example.clement.emm_project2.activities.StartedVideosActivity;
import com.example.clement.emm_project2.activities.SubCatActivity;
import com.example.clement.emm_project2.adapters.DrawerAdapter;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        DataAccess dataAccess = new DataAccess(this);
        categories = dataAccess.getAllDatas(Category.class);

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
                resetTitle();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.drawer_menu);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    protected abstract void resetTitle();

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

    protected void onNavItemSelected(int id) {
        Log.d("OUOU", "CLICKED ID => " + id);
        if(id < 100 ) {
            Category cat = categories.get(id);

            Intent i = new Intent(this, SubCatActivity.class);
            i.putExtra("desc", cat.getDescription());
            i.putExtra("title", cat.getTitle());
            i.putExtra("catId", cat.getMongoID());

            startActivity(i);
        }
        Intent i;
        switch(id) {
            case 301:
                i = new Intent(this, FavoriteActivity.class);
                startActivity(i);
                break;
            case 302:
                i = new Intent(this, StartedVideosActivity.class);
                startActivity(i);
                break;
        }
    };

    protected void addCategories() {
        Collections.sort(categories, new Comparator<Category>() {
            public int compare(Category c1, Category c2) {
                String t1 = c1.getTitle().toUpperCase();
                String t2 = c2.getTitle().toUpperCase();
                return t1.compareTo(t2);
            }
        });
        this.drawerItems.add(DrawerSection.create(200, "Catégories", "ic_action_bookmark", this));
        for(Category category : categories) {
            this.drawerItems.add(DrawerSectionItem.create(categories.indexOf(category), category.getTitle(), true));
        }
    }

    protected void addFavoritesAndUserTravel(){
        this.drawerItems.add(DrawerSection.create(300, "Navigation", "ic_action_label", this));
        this.drawerItems.add(DrawerSectionItem.create(301, "Favoris", true));
        this.drawerItems.add(DrawerSectionItem.create(302, "Parcours", true));
    }

}
