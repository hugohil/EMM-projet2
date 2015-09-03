package com.example.clement.emm_project2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.SubCatListAdapter;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.app.drawer.DrawerItem;
import com.example.clement.emm_project2.app.drawer.DrawerSection;
import com.example.clement.emm_project2.app.drawer.DrawerSectionItem;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavoriteActivity extends DrawerActivity {
    private final static String TAG = FavoriteActivity.class.getSimpleName();

    private ArrayList<SubCategory> subCats = new ArrayList<SubCategory>();
    private SubCatListAdapter adapter;
    private ListView listView;
    private DataAccess dataAccess;
    private List<Category> categories = new ArrayList<Category>();
    private SharedPrefUtil sharedPref = new SharedPrefUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.favorite_title);

        adapter = new SubCatListAdapter(this, subCats);
        listView = (ListView) findViewById(R.id.act_fav_list);
        listView.setAdapter(adapter);
        dataAccess = new DataAccess(getBaseContext());

        // All this needs to be in an Async task (activity is doin' to much work on his main Thread ... Skipps maaaany frames)
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
        menuItems.add(DrawerSection.create(200, "Catégories", "ic_action_bookmark", FavoriteActivity.this));
        for(Category category : dbCategories) {
            menuItems.add(DrawerSectionItem.create(dbCategories.indexOf(category), category.getTitle(), true));
        }
        menuItems.add(DrawerSection.create(100, "Configuration", "ic_action_settings", FavoriteActivity.this));
        menuItems.add(DrawerSectionItem.create(101, "Preferences", true));
        setDrawerContent(menuItems);
        categories.addAll(dbCategories);

        bindView();
    }

    @Override
    protected void resetTitle(){
        getSupportActionBar().setTitle(R.string.favorite_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
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

    public void bindView() {
        List<SubCategory> favList = sharedPref.getFavoritesFormations();
        Log.d(TAG, favList.toString());
        if(favList.size() > 0){
            Collections.sort(favList, new Comparator<SubCategory>() {
                public int compare(SubCategory c1, SubCategory c2) {
                    String t1 = c1.getTitle().toUpperCase();
                    String t2 = c2.getTitle().toUpperCase();
                    return t1.compareTo(t2);
                }
            });
            subCats.clear();
            subCats.addAll(favList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_favorite;
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
}
