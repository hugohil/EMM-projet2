package com.example.clement.emm_project2.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.VideosListAdapter;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StartedVideosActivity extends DrawerActivity {
    private final static String TAG = StartedVideosActivity.class.getSimpleName();

    private ArrayList<Formation> videos = new ArrayList<Formation>();
    private VideosListAdapter adapter;
    private RecyclerView recyclerView;
    private DataAccess dataAccess;
    private List<Category> categories = new ArrayList<Category>();
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addFavoritesAndUserTravel();
        super.addCategories();
        setTitle(R.string.started_videos_title);

        dataAccess = new DataAccess(this);
        List<Formation> formationsList = new ArrayList<Formation>();
        List<Item> itemList = new ArrayList<Item>();

        Set<String> videosIDs = SharedPrefUtil.getPendingFormationIds();
        Log.d(TAG, "videosIDs: "+videosIDs);
        for (String id : videosIDs){
            Log.d(TAG, "id: "+id);
            Formation formation = (Formation) dataAccess.findDataWhere(Formation.class, "mongoid", id).get(0);
            assert formation != null;
            formationsList.add(formation);
        }
        Log.d(TAG, formationsList.toString());

        if(formationsList.size() > 0){
            recyclerView = (RecyclerView) findViewById(R.id.act_videos_recycler);
            recyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            adapter = new VideosListAdapter(formationsList);
            recyclerView.setAdapter(adapter);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(App.getAppContext(), R.style.alertDialogStyle);
            builder.setTitle(R.string.dialog_error);
            builder.setMessage(R.string.videos_started_nope);
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_started_videos, menu);
        return true;
    }

    @Override
    protected void resetTitle(){
        getSupportActionBar().setTitle(R.string.started_videos_title);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_started_videos;
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
}
