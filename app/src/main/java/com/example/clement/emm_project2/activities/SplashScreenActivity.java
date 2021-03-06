package com.example.clement.emm_project2.activities;

import android.accounts.Account;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.data.database.DatabaseHelper;
import com.example.clement.emm_project2.sync.SyncAdapter;
import com.example.clement.emm_project2.util.SharedPrefUtil;
import com.example.clement.emm_project2.util.SyncUtil;

public class SplashScreenActivity extends Activity {
    private final String TAG = SplashScreenActivity.class.getSimpleName();

    private SharedPrefUtil sharedPref = new SharedPrefUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        registerReceiver(syncFinishedReceiver, new IntentFilter(SyncAdapter.SYNC_FINISHED));

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.upgradeDbIfNecessary();

        // TODO : check last sync date
        // If none => run sync manually
        // Then, get app preferences, and compare last sync date with sheduled date / period (run sync if necessary)
        if (!SharedPrefUtil.didSynchroRunOnce()) {
            Log.d(TAG, "CATEGORIES NOT FOUND IN CACHE");
            Log.d(TAG, "trying to run sync");
            Account account = SyncUtil.CreateSyncAccount(this);
            ContentResolver.setSyncAutomatically(account, "com.example.clement.emm_project2.datasync.provider", true);
            ContentResolver.requestSync(account, "com.example.clement.emm_project2.datasync.provider", new Bundle());
        } else {
            redirect();
        }

    }

    private void redirect() {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        if(SharedPrefUtil.areFavoriteFormations()){
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private BroadcastReceiver syncFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            redirect();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    protected void onPause() {
        unregisterReceiver(syncFinishedReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(syncFinishedReceiver, new IntentFilter(SyncAdapter.SYNC_FINISHED));
        super.onResume();
    }
}
