package com.example.clement.emm_project2.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.SubCatListAdapter;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.SharedPrefUtil;
import com.example.clement.emm_project2.util.StringUtil;
import com.ms.square.android.expandabletextview.ExpandableTextView;

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
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addFavoritesAndUserTravel();
        super.addCategories();

        adapter = new SubCatListAdapter(this, subCats);
        listView = (ListView) findViewById(R.id.act_subcat_list);
        listView.setAdapter(adapter);

        dataAccess = new DataAccess(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title = extras.getString("title");
            bindView(extras.getString("desc"), title, extras.getString("catId"));
        } else {
            throw new RuntimeException("No intent extras ! Cannot find targeted category !! ");
        }
    }

    @Override
    protected void resetTitle(){
        getSupportActionBar().setTitle(getString(R.string.formations) + " " + title);
    }

    public void bindView(String description, String title, String catId) {
        ExpandableTextView desc = (ExpandableTextView) findViewById(R.id.act_subcat_desc);
        String html = StringUtil.html2Text(description); // Needs to be done in DataAccess?
        desc.setText(Html.fromHtml(html));
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
        if (id == R.id.action_delete_user_preferences) {
            SharedPrefUtil.handleUserPreferencesDelete(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sub_cat;
    }

}
