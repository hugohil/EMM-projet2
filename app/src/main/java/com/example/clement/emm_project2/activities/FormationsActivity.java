package com.example.clement.emm_project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.FormationListAdapter;
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
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FormationsActivity extends DrawerActivity {

    private static final String TAG = FormationsActivity.class.getSimpleName();
    private final Context context = this;
    private DataAccess dataAccess;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Switch favoriteSwitch;
    private SharedPrefUtil sharedPref = new SharedPrefUtil();
    private List<Category> categories = new ArrayList<Category>();
    private List<Formation> subCatFormations = new ArrayList<Formation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataAccess = new DataAccess(this);
        categories = dataAccess.getAllDatas(Category.class);
        favoriteSwitch = (Switch) findViewById(R.id.act_formation_fav_switch);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FormationListAdapter((ArrayList<Formation>)subCatFormations);
        mRecyclerView.setAdapter(mAdapter);
        final Context context = this;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final String subCatId= extras.getString("subCatId");
            ServerHandler server = new ServerHandler(App.getAppContext());
            server.getFormations(subCatId, new ResponseHandler() {
                @Override
                public void onSuccess(Object datas) {
                    List<Formation> formations = JsonUtil.parseJsonDatas((JSONArray) datas, Formation.class);
                    if(formations.size() == 1) { // needs to be done before starting this activity
                        Intent intent = new Intent(context, FormationActivity.class);
                        intent.putExtra("ean", formations.get(0).getEan());
                        context.startActivity(intent);
                    }
                    subCatFormations.addAll(formations);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String error) {
                    Log.wtf(TAG, error);
                }
            });

            if(sharedPref.isFormationFavorited(subCatId)){
                favoriteSwitch.setChecked(true);
            }

            favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // TODO: register formation ID instead of subcategory ID, with formation name instead of this class name.
                        sharedPref.addFavoriteFormation(subCatId);
                    } else {
                        sharedPref.removeFavoriteFormation(subCatId);
                    }
                }
            });

        } else {
            throw new RuntimeException("No intent extras ! Cannot find targeted category !! ");
        }

        ArrayList<DrawerItem> menuItems = new ArrayList<DrawerItem>();
        Collections.sort(categories, new Comparator<Category>() {
            public int compare(Category c1, Category c2) {
                String t1 = c1.getTitle().toUpperCase();
                String t2 = c2.getTitle().toUpperCase();
                return t1.compareTo(t2);
            }
        });
        if(SharedPrefUtil.areFavoriteFormations()) {
            menuItems.add(DrawerSection.create(300, "Navigation", "ic_action_label", this));
            menuItems.add(DrawerSectionItem.create(301, "Favoris", true));
        }
        menuItems.add(DrawerSection.create(200, "Catégories", "ic_action_bookmark", this));
        for(Category category : categories) {
            menuItems.add(DrawerSectionItem.create(categories.indexOf(category), category.getTitle(), true));
        }
        menuItems.add(DrawerSection.create(100, "Configuration", "ic_action_settings", this));
        menuItems.add(DrawerSectionItem.create(101, "Preferences", true));
        setDrawerContent(menuItems);
    }

    @Override
    protected void resetTitle(){
        getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formations, menu);
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
    protected void onResume() {
        super.onResume();
        /*((FormationListAdapter) mAdapter).setOnItemClickListener(new FormationListAdapter.FormationClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(TAG, " Clicked on Item " + position);
            }
        });*/
    }

    protected int getLayoutResourceId() {
        return R.layout.activity_formations;
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
        if(id > 300){
            Intent i = new Intent(this, FavoriteActivity.class);
            startActivity(i);
        }
    }

}
