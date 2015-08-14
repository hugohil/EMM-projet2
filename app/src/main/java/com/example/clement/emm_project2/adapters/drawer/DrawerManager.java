package com.example.clement.emm_project2.adapters.drawer;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.clement.emm_project2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 13/08/15.
 */

// Encapsulate drawer logic
public class DrawerManager {
    private RecyclerView mRecyclerView;
    private DrawerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout drawer;

    public DrawerManager(List<String> itemTitles, int[] icons, Activity activity) {
        this.mRecyclerView = (RecyclerView) activity.findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DrawerAdapter((ArrayList)itemTitles, icons, activity);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        drawer = (DrawerLayout) activity.findViewById(R.id.DrawerLayout);
    }

    public void setItemTouchListener(RecyclerView.OnItemTouchListener itemTouchListener) {
        this.mRecyclerView.addOnItemTouchListener(itemTouchListener);
    }

    public void closeDrawer() {
        drawer.closeDrawers();
    }

}
