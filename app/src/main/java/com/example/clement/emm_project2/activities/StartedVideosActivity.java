package com.example.clement.emm_project2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.FormationListAdapter;
import com.example.clement.emm_project2.adapters.ItemListAdapter;
import com.example.clement.emm_project2.adapters.VideosListAdapter;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.app.drawer.DrawerItem;
import com.example.clement.emm_project2.app.drawer.DrawerSection;
import com.example.clement.emm_project2.app.drawer.DrawerSectionItem;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StartedVideosActivity extends DrawerActivity {
    private final static String TAG = StartedVideosActivity.class.getSimpleName();

    private ArrayList<Item> videos = new ArrayList<Item>();
    private VideosListAdapter adapter;
    private RecyclerView recyclerView;
    private DataAccess dataAccess;
    private List<Category> categories = new ArrayList<Category>();
    private SharedPrefUtil sharedPref = new SharedPrefUtil();
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.started_videos_title);

        ArrayList<Item> videosList = sharedPref.getStartedVideos();
        Log.d(TAG, videosList.toString());

        recyclerView = (RecyclerView) findViewById(R.id.act_videos_recycler);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new VideosListAdapter(videos);
        recyclerView.setAdapter(adapter);

        if(videosList.size() > 0){
            Collections.sort(videosList, new Comparator<Item>() {
                public int compare(Item c1, Item c2) {
                    String t1 = c1.getTitle().toUpperCase();
                    String t2 = c2.getTitle().toUpperCase();
                    return t1.compareTo(t2);
                }
            });
            videos.clear();
            videos.addAll(videosList);
            adapter.notifyDataSetChanged();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(App.getAppContext(), R.style.alertDialogStyle);
            builder.setTitle(R.string.dialog_error);
            builder.setMessage(R.string.videos_started_nope);
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
        }

        ArrayList<DrawerItem> menuItems = new ArrayList<DrawerItem>();
        List<Category> dbCategories;
        dataAccess = new DataAccess(this);
        dbCategories = dataAccess.getAllDatas(Category.class);
        Collections.sort(dbCategories, new Comparator<Category>() {
            public int compare(Category c1, Category c2) {
                String t1 = c1.getTitle().toUpperCase();
                String t2 = c2.getTitle().toUpperCase();
                return t1.compareTo(t2);
            }
        });
        menuItems.add(DrawerSection.create(200, "Catégories", "ic_action_bookmark", StartedVideosActivity.this));
        for(Category category : dbCategories) {
            menuItems.add(DrawerSectionItem.create(dbCategories.indexOf(category), category.getTitle(), true));
        }
        menuItems.add(DrawerSection.create(100, "Configuration", "ic_action_settings", StartedVideosActivity.this));
        menuItems.add(DrawerSectionItem.create(101, "Preferences", true));
        setDrawerContent(menuItems);
        categories.addAll(dbCategories);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_started_videos, menu);
        return true;
    }

    @Override
    protected void resetTitle(){
        getSupportActionBar().setTitle(R.string.started_videos_title);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_started_videos;
    }

    @Override
    protected void onNavItemSelected(int id) {
        if(id < 100 ) {
            Category cat = categories.get(id);

            Intent i = new Intent(this, SubCatActivity.class);
            i.putExtra("desc", cat.getDescription());
            i.putExtra("title", cat.getTitle());
            i.putExtra("catId", cat.getMongoID());

            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
