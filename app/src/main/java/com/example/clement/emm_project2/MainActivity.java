package com.example.clement.emm_project2;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.example.clement.emm_project2.adapters.CatListAdapter;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.app.drawer.DrawerItem;
import com.example.clement.emm_project2.app.drawer.DrawerSection;
import com.example.clement.emm_project2.app.drawer.DrawerSectionItem;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;

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

        ArrayList<DrawerItem> menuItems = new ArrayList<DrawerItem>();
        menuItems.add(DrawerSection.create(100, "Configuration", "ic_action_settings", MainActivity.this));
        menuItems.add(DrawerSectionItem.create(101, "Preferences", true));
        setDrawerContent(menuItems);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNavItemSelected(int id) {

    }
}
