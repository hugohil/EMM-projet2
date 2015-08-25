package com.example.clement.emm_project2.activities;

import android.accounts.Account;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.data.database.DatabaseHelper;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.app.server.ResponseHandler;
import com.example.clement.emm_project2.app.server.ServerHandler;
import com.example.clement.emm_project2.sync.StubContentProvider;
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.SharedPrefUtil;
import com.example.clement.emm_project2.util.SyncUtil;

import org.json.JSONArray;

import java.util.List;

public class SplashScreenActivity extends Activity {
    private final String TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView progressText = (TextView) findViewById(R.id.progressText);
        ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.upgradeDbIfNecessary();

        Intent intent = new Intent(this, MainActivity.class);

        // TODO : check last sync date
        // If none => run sync manually
        // Then, get app preferences, and compare last sync date with sheduled date / period (run sync if necessary)
        if (!SharedPrefUtil.areCategoriesInCache()) {
            Log.d(TAG, "trying to run sync");
            Account account = SyncUtil.CreateSyncAccount(this);
            ContentResolver.requestSync(account,"com.example.clement.emm_project2.datasync.provider", new Bundle());
        } else {
            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }
}
