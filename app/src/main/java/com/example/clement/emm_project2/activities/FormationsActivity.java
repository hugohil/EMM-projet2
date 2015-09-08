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

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.FormationListAdapter;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.app.drawer.DrawerItem;
import com.example.clement.emm_project2.app.drawer.DrawerSection;
import com.example.clement.emm_project2.app.drawer.DrawerSectionItem;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FormationsActivity extends DrawerActivity {

    private static final String TAG = FormationsActivity.class.getSimpleName();
    private final Context context = this;
    private DataAccess dataAccess;
    private RecyclerView mRecyclerView;
    private FormationListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPrefUtil sharedPref = new SharedPrefUtil();
    private List<Category> categories = new ArrayList<Category>();
    private List<Formation> subCatFormations = new ArrayList<Formation>();
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataAccess = new DataAccess(this);
        categories = dataAccess.getAllDatas(Category.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FormationListAdapter((ArrayList<Formation>)subCatFormations);
        mAdapter.setOnCardClickListener(new FormationListAdapter.FormationClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String formationEan = mAdapter.getFormationEan(position);
                redirectToSingleView(formationEan);

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        final Context context = this;

        final DataAccess da = new DataAccess(App.getAppContext());
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title = extras.getString("subCatTitle");
            setTitle(title);
            final String subCatId= extras.getString("subCatId");
            List<Formation> dbFormations = da.findDataWhere(Formation.class, "subCatId", subCatId);
            if(dbFormations.isEmpty()) {
                Log.d(TAG, "No formations ... strange");
            } else {
                Log.d(TAG, "Got Formations in db");
                Log.d(TAG, "SIZE =>"+dbFormations.size());
                subCatFormations.addAll(dbFormations);
                mAdapter.notifyDataSetChanged();
            }



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
        menuItems.add(DrawerSection.create(300, "Navigation", "ic_action_label", this));
        menuItems.add(DrawerSectionItem.create(301, "Favoris", true));
        menuItems.add(DrawerSectionItem.create(302, "Parcours", true));
        menuItems.add(DrawerSection.create(200, "Catégories", "ic_action_bookmark", this));
        for(Category category : categories) {
            menuItems.add(DrawerSectionItem.create(categories.indexOf(category), category.getTitle(), true));
        }
        menuItems.add(DrawerSection.create(100, "Configuration", "ic_action_settings", this));
        menuItems.add(DrawerSectionItem.create(101, "Preferences", true));
        setDrawerContent(menuItems);
    }

    private void redirectToSingleView(String formationEan) {
        Intent intent = new Intent(context, FormationSummaryActivity.class);
        intent.putExtra("ean", formationEan);
        context.startActivity(intent);
    }

    @Override
    protected void resetTitle(){
        getSupportActionBar().setTitle(title);
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
            if(id > 301) {
                Intent i = new Intent(this, StartedVideosActivity.class);
                startActivity(i);
            }
            Intent i = new Intent(this, FavoriteActivity.class);
            startActivity(i);
        }
    }

}
