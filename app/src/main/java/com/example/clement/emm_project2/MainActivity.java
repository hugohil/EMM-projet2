package com.example.clement.emm_project2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.clement.emm_project2.adapters.CatListAdapter;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Category> categories = new ArrayList<Category>();;
    private ListView listView;
    private CatListAdapter adapter;
    private DataAccess dataAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
