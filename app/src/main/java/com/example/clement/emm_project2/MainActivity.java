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

import com.example.clement.emm_project2.adapters.CatListAdapter;
import com.example.clement.emm_project2.adapters.drawer.DrawerManager;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Category> categories = new ArrayList<Category>();
    private ListView listView;
    private CatListAdapter adapter;
    private DataAccess dataAccess;

    ArrayList<String> drawerTitles = new ArrayList<String>();
    int drawerIcons[] = {R.drawable.ic_action_settings};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerTitles.add("Preferences");

        final DrawerManager drawerManager = new DrawerManager(drawerTitles, drawerIcons, this);
        drawerManager.setItemTouchListener(new RecyclerView.OnItemTouchListener() {
            final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    switch(recyclerView.getChildLayoutPosition(child)) {
                        case 1:
                            Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
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

        dataAccess = new DataAccess(getBaseContext());

        // Set listView adapter
        adapter = new CatListAdapter(this, categories);
        listView = (ListView) findViewById(R.id.act_main_listView);
        listView.setAdapter(adapter);

        // Getting categories
        dataAccess.open();
        List<Category> dbCategories =  dataAccess.getAllDatas(Category.class);
        dataAccess.close();

        // We need to use addAll here because with 'categories = dbCategories' adapter loses references to the list :/
        categories.addAll(dbCategories);
        Log.wtf(TAG, "Size=" + categories.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
