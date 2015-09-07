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
import com.example.clement.emm_project2.app.server.ResponseHandler;
import com.example.clement.emm_project2.app.server.ServerHandler;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FormationDetailActivity extends AppCompatActivity {

    private final static String TAG = FormationDetailActivity.class.getSimpleName();
    public ItemListAdapter adapter;
    ExpandableListView expListView;
    List<Item> chapters = new ArrayList<Item>();
    HashMap<Item, List<Item>> videos = new HashMap<Item, List<Item>>();
    private Context context = this;
    private SharedPrefUtil sharedPref = new SharedPrefUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final String eanCode= extras.getString("ean");
            ServerHandler server = new ServerHandler(App.getAppContext());
            server.getFormation(eanCode, new ResponseHandler() {
                @Override
                public void onSuccess(Object datas) {
                    Formation formation = JsonUtil.parseJsonData((JSONObject) datas, Formation.class);
                    getSupportActionBar().setTitle(formation.getTitle());
                    displayFormation(formation);
                }

                @Override
                public void onError(String error) {
                    Log.wtf(TAG, error);
                }
            });
        }
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
                Log.d(TAG, "item.mongoID: "+item.getMongoID());
                String ids = formation.getEan() + "," + item.getMongoID(); // My eyes ... they hurt :'( !
                sharedPref.addStartedVideo(ids);
                Log.d(TAG, "FIELD VIDEO =>"+item.getFieldVideo().get(0).get("filepath"));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse((String)item.getFieldVideo().get(0).get("filepath")), "video/*");
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
}
