package com.example.clement.emm_project2.adapters.drawer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.clement.emm_project2.MainActivity;
import com.example.clement.emm_project2.R;

/**
 * Created by Clement on 13/08/15.
 */
// Encapsulate drawer logic
public class DrawerManager {
    private RecyclerView mRecyclerView;
    private DrawerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout drawer;

    public DrawerManager(String[] itemTitles, int[] icons, Activity activity) {
        this.mRecyclerView = (RecyclerView) activity.findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DrawerAdapter(itemTitles, icons, activity);
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
