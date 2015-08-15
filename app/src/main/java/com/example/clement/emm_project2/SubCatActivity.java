package com.example.clement.emm_project2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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
        dataAccess.open();
        dbCategories = dataAccess.getAllDatas(Category.class);
        dataAccess.close();
        menuItems.add(DrawerSection.create(200, "Catégories", "ic_action_bookmark", SubCatActivity.this));
        int i = 1;
        for(Category category : dbCategories) {
            menuItems.add(DrawerSectionItem.create(dbCategories.indexOf(category), category.getTitle(), true));
            i++;
        }
        menuItems.add(DrawerSection.create(100, "Configuration", "ic_action_settings", SubCatActivity.this));
        menuItems.add(DrawerSectionItem.create(101, "Preferences", true));
        setDrawerContent(menuItems);
        categories.addAll(dbCategories);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            TextView desc = (TextView) findViewById(R.id.act_subcat_desc);
            String html = StringUtil.html2Text(extras.getString("desc"));
            desc.setText(html);
            setTitle(extras.getString("title"));
            try{
                dataAccess.open();
                List<SubCategory> DBList = dataAccess.findDataWhere(SubCategory.class, "catId", extras.getString("catID"));
                dataAccess.close();
                subCats.addAll(DBList);
                Log.d(SubCatActivity.class.getSimpleName(), "" + subCats.size());
                adapter.notifyDataSetChanged();
            } catch(Exception e){
                Log.e(SubCatActivity.class.getSimpleName(), e.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_cat, menu);
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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sub_cat;
    }

    @Override
    protected void onNavItemSelected(int id) {
        if(id < 100 ){
            // Click on categories
            Category category = categories.get(id);
            Intent i = new Intent(this, SubCatActivity.class);
            i.putExtra("desc", category.getDescription());
            i.putExtra("title", category.getTitle());
            i.putExtra("catID", category.getMongoID());
            this.startActivity(i);
        }
    }
}
