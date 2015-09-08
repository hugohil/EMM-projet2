package com.example.clement.emm_project2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

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

public class FavoriteActivity extends DrawerActivity {
    private final static String TAG = FavoriteActivity.class.getSimpleName();

    private ArrayList<Formation> formations = new ArrayList<Formation>();
    private FormationListAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DataAccess dataAccess;
    private List<Category> categories = new ArrayList<Category>();
    private SharedPrefUtil sharedPref = new SharedPrefUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.favorite_title);

        recyclerView = (RecyclerView) findViewById(R.id.act_fav_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new FormationListAdapter(formations);
        adapter.setOnCardClickListener(new FormationListAdapter.FormationClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String formationEan = adapter.getFormationEan(position);
                redirectToSingleView(formationEan);

            }
        });
        recyclerView.setAdapter(adapter);
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
        menuItems.add(DrawerSection.create(300, "Navigation", "ic_action_label", this));
        menuItems.add(DrawerSectionItem.create(301, "Parcours", true));
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

    private void redirectToSingleView(String formationEan) {
        Intent intent = new Intent(this, FormationSummaryActivity.class);
        intent.putExtra("ean", formationEan);
        this.startActivity(intent);
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
        if (id == R.id.action_delete_user_preferences) {
            SharedPrefUtil.handleUserPreferencesDelete(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void bindView() {
        List<Formation> favList = sharedPref.getFavoritesFormations();
        if(favList != null && favList.size() > 0){
            Collections.sort(favList, new Comparator<Formation>() {
                public int compare(Formation c1, Formation c2) {
                    String t1 = c1.getTitle().toUpperCase();
                    String t2 = c2.getTitle().toUpperCase();
                    return t1.compareTo(t2);
                }
            });
            formations.clear();
            formations.addAll(favList);
            adapter.notifyDataSetChanged();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(App.getAppContext(), R.style.alertDialogStyle);
            builder.setTitle(R.string.dialog_error);
            builder.setMessage(R.string.favorite_activity_nope);
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
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
        if(id > 300){
            Intent i = new Intent(this, StartedVideosActivity.class);
            startActivity(i);
        }
    }
}
