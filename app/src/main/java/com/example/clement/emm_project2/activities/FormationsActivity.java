package com.example.clement.emm_project2.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.adapters.FormationListAdapter;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Formation;

import java.util.ArrayList;
import java.util.List;

public class FormationsActivity extends ActionBarActivity {

    private static final String TAG = FormationsActivity.class.getSimpleName();
    private DataAccess dataAccess = new DataAccess(this);
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String subCatId = extras.getString("subCatId");
            List<Formation> formations = dataAccess.findDataWhere(Formation.class, "subCatId", subCatId);


            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new FormationListAdapter((ArrayList<Formation>)formations);
            mRecyclerView.setAdapter(mAdapter);

            // Code to Add an item with default animation
            //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

            // Code to remove an item with default animation
            //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);


        } else {
            throw new RuntimeException("No intent extras ! Cannot find targeted category !! ");
        }
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
        ((FormationListAdapter) mAdapter).setOnItemClickListener(new FormationListAdapter.FormationClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(TAG, " Clicked on Item " + position);
            }
        });
    }

}
