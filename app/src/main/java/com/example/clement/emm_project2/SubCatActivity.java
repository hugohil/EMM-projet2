package com.example.clement.emm_project2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clement.emm_project2.adapters.SubCatListAdapter;
import com.example.clement.emm_project2.adapters.drawer.DrawerManager;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


public class SubCatActivity extends ActionBarActivity {

    private static final String TAG = SubCatActivity.class.getSimpleName();

    private ArrayList<SubCategory> subCats = new ArrayList<SubCategory>();
    private SubCatListAdapter adapter;
    private ListView listView;

    // Drawer settings
    List<String> drawerTitles = new ArrayList<String>();
    int drawerIcons[] = {R.drawable.ic_action_settings, };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat);

        drawerTitles.add("Preferences");

        DataAccess dataAccess = new DataAccess(this);
        dataAccess.open();
        List<Category> categories = dataAccess.getAllDatas(Category.class);
        for(Category cat : categories) {
            drawerTitles.add(cat.getTitle());
        }


        final DrawerManager drawerManager = new DrawerManager(drawerTitles, drawerIcons, this);
        drawerManager.setItemTouchListener(new RecyclerView.OnItemTouchListener() {
            final GestureDetector mGestureDetector = new GestureDetector(SubCatActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    switch (recyclerView.getChildLayoutPosition(child)) {
                        case 1:
                            Intent intent = new Intent(SubCatActivity.this, PreferencesActivity.class);
                            startActivity(intent);
                            break;
                    }
                    drawerManager.closeDrawer();
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                //
            }

        });


        listView = (ListView) findViewById(R.id.act_subcat_list);
        adapter = new SubCatListAdapter(this, subCats);
        listView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            TextView desc = (TextView) findViewById(R.id.act_subcat_desc);
            String html = StringUtil.html2Text(extras.getString("desc"));
            desc.setText(html);
            try{
                ObjectMapper mapper = new ObjectMapper();
                subCats = (ArrayList<SubCategory>) mapper.readValue(extras.getString("subcat"), ArrayList.class);
                Log.d(SubCatActivity.class.getSimpleName(), ""+subCats.size());
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
}
