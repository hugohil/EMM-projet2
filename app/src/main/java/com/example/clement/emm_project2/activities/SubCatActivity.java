package com.example.clement.emm_project2.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.SubCatListAdapter;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.app.drawer.DrawerItem;
import com.example.clement.emm_project2.app.drawer.DrawerSection;
import com.example.clement.emm_project2.app.drawer.DrawerSectionItem;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SubCatActivity extends DrawerActivity {

    private static final String TAG = SubCatActivity.class.getSimpleName();

    private ArrayList<SubCategory> subCats = new ArrayList<SubCategory>();
    private SubCatListAdapter adapter;
    private ListView listView;
    private DataAccess dataAccess;
    private List<Category> categories = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new SubCatListAdapter(this, subCats);
        listView = (ListView) findViewById(R.id.act_subcat_list);
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
        menuItems.add(DrawerSection.create(200, "Catégories", "ic_action_bookmark", SubCatActivity.this));
        for(Category category : dbCategories) {
            menuItems.add(DrawerSectionItem.create(dbCategories.indexOf(category), category.getTitle(), true));
        }
        menuItems.add(DrawerSection.create(100, "Configuration", "ic_action_settings", SubCatActivity.this));
        menuItems.add(DrawerSectionItem.create(101, "Preferences", true));
        setDrawerContent(menuItems);
        categories.addAll(dbCategories);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            bindView(extras.getString("desc"), extras.getString("title"), extras.getString("catId"));
        } else {
            throw new RuntimeException("No intent extras ! Cannot find targeted category !! ");
        }
    }

    public void bindView(String description, String title, String catId) {
        TextView desc = (TextView) findViewById(R.id.act_subcat_desc);
        String html = StringUtil.html2Text(description); // Needs to be done in DataAccess?
        desc.setText(html);
        setTitle(title);

        // Set subcategories list
        List<SubCategory> DBList = dataAccess.findDataWhere(SubCategory.class, "catId", catId);
        Collections.sort(DBList, new Comparator<SubCategory>() {
            public int compare(SubCategory c1, SubCategory c2) {
                String t1 = c1.getTitle().toUpperCase();
                String t2 = c2.getTitle().toUpperCase();
                return t1.compareTo(t2);
            }
        });
        Log.d(TAG, "DbList size : " + DBList.size());
        subCats.clear();
        subCats.addAll(DBList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_cat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sub_cat;
    }

    @Override
    protected void onNavItemSelected(int id) {
        if(id < 100 ){
            // Click on categories
            Category category = categories.get(id);
            bindView(category.getDescription(), category.getTitle(), category.getMongoID());
            getSupportActionBar().setTitle(category.getTitle());
        }
    }
}
