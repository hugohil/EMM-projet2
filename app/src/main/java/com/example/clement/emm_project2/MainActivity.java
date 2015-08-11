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
import com.example.clement.emm_project2.model.Author;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.server.ResponseHandler;
import com.example.clement.emm_project2.server.ServerHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private List<Category> categories;
    private ListView listView;
    private CatListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.act_main_listView);
        categories = new ArrayList<Category>();
        adapter = new CatListAdapter(this, categories);

        ServerHandler server = new ServerHandler(this);
        server.getCategories(new ResponseHandler() {
            @Override
            public void onSuccess(Object datas) {
                parseJSONDatas((JSONArray) datas);
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
                Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        // populateListView(categories);
    }

    private void parseJSONDatas(JSONArray json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            DataAccess da = new DataAccess(getBaseContext());
            for (int i = 0; i < json.length(); i++) {
                da.open();
                Category cat = mapper.readValue(json.getJSONObject(i).toString(), Category.class);
                categories.add(cat);
                da.createCategory(cat);
                da.close();
            }
            adapter.notifyDataSetChanged();
        } catch (Exception error) {
            Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // private void populateListView(List<Category> catList){
        // adapter.notifyDataSetChanged();
    // }

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
