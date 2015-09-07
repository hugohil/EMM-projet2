package com.example.clement.emm_project2.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.ItemListAdapter;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.app.drawer.DrawerItem;
import com.example.clement.emm_project2.app.drawer.DrawerSection;
import com.example.clement.emm_project2.app.drawer.DrawerSectionItem;
import com.example.clement.emm_project2.app.server.ResponseHandler;
import com.example.clement.emm_project2.app.server.ServerHandler;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class FormationDetailActivity extends DrawerActivity {

    private final static String TAG = FormationDetailActivity.class.getSimpleName();
    public ItemListAdapter adapter;
    ExpandableListView expListView;
    List<Item> chapters = new ArrayList<Item>();
    HashMap<Item, List<Item>> videos = new HashMap<Item, List<Item>>();
    private Context context = this;
    private SharedPrefUtil sharedPref = new SharedPrefUtil();
    private Formation formation = new Formation();
    private List<Category> categories = new ArrayList<Category>();
    private DataAccess dataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final String eanCode= extras.getString("ean");
            DataAccess da = new DataAccess(App.getAppContext());
            List<Formation> formations = da.findDataWhere(Formation.class, "ean", eanCode);
            formation = formations.get(0);
            displayFormation(formation);
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
        if(SharedPrefUtil.areFavoriteFormations()) {
            menuItems.add(DrawerSection.create(300, "Navigation", "ic_action_label", this));
            menuItems.add(DrawerSectionItem.create(301, "Favoris", true));
            menuItems.add(DrawerSectionItem.create(302, "Parcours", true));
        }
        menuItems.add(DrawerSection.create(200, "Catégories", "ic_action_bookmark", FormationDetailActivity.this));
        for(Category category : dbCategories) {
            menuItems.add(DrawerSectionItem.create(dbCategories.indexOf(category), category.getTitle(), true));
        }
        menuItems.add(DrawerSection.create(100, "Configuration", "ic_action_settings", FormationDetailActivity.this));
        menuItems.add(DrawerSectionItem.create(101, "Preferences", true));
        setDrawerContent(menuItems);
        categories.addAll(dbCategories);
    }

    public void displayFormation(final Formation formation) {
        ArrayList<Item> items = (ArrayList)formation.getItems();
        expListView = (ExpandableListView) findViewById(R.id.itemsListView);
        prepareItemsLists(items);
        adapter = new ItemListAdapter(this, chapters, videos);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Item item = videos.get(chapters.get(groupPosition)).get(childPosition);
                Log.d(TAG, "item.mongoID: " + item.getMongoID());
                String ids = formation.getEan() + "," + item.getMongoID(); // My eyes ... they hurt :'( !
                sharedPref.addStartedVideo(ids);
                Log.d(TAG, "FIELD VIDEO =>" + item.getFieldVideo().get(0).get("filepath"));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse((String) item.getFieldVideo().get(0).get("filepath")), "video/*");
                context.startActivity(intent);

                return false;
            }
        });
        expListView.setAdapter(adapter);
    }

    private void prepareItemsLists(List<Item> items) {
        for(Item item : items) {
            if(item.getType().equals("chapter")) {
                List<Item> childrens = new ArrayList<Item>();
                for(Item item2: items) {
                    if(item.getChildrens().contains(item2.getMongoId())) {
                        childrens.add(item2);
                    }
                }
                chapters.add(item);
                videos.put(item, childrens);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formation_detail, menu);
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
    protected void resetTitle(){
        setTitle(formation.getTitle());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_formation_detail;
    }

    @Override
    protected void onNavItemSelected(int id) {
        if(id < 100 ){
            // Click on categories
            Category cat = categories.get(id);

            Intent i = new Intent(this, SubCatActivity.class);
            i.putExtra("desc", cat.getDescription());
            i.putExtra("title", cat.getTitle());
            i.putExtra("catId", cat.getMongoID());

            startActivity(i);
        }
        if(id > 300){
            if(id > 301){
                Intent i = new Intent(this, StartedVideosActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, FavoriteActivity.class);
                startActivity(i);
            }
        }
    }
}
