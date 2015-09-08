package com.example.clement.emm_project2.activities;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.CatListAdapter;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends DrawerActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Category> categories = new ArrayList<Category>();
    private ListView listView;
    private CatListAdapter adapter;
    private DataAccess dataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // No need to setContentView since it is done in the superClass =)
        super.onCreate(savedInstanceState);
        super.addFavoritesAndUserTravel();
        super.addCategories();
        displayCategories();
    }

    private void displayCategories() {
        // Get categories
        List<Category> dbCategories;
        dataAccess = new DataAccess(this);
        dbCategories = dataAccess.getAllDatas(Category.class);
        categories.addAll(dbCategories);

        // Set listView adapter
        adapter = new CatListAdapter(this, categories);
        listView = (ListView) findViewById(R.id.act_main_listView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void resetTitle(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_user_preferences) {
            SharedPrefUtil.handleUserPreferencesDelete(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }
}
