package com.example.clement.emm_project2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.server.ResponseHandler;
import com.example.clement.emm_project2.server.ServerHandler;
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// TODO : remove ActionBarActivity and extend normal Activity
public class SplashScreenActivity extends ActionBarActivity {


    private final String TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final TextView progressText = (TextView) findViewById(R.id.progressText);
        final ProgressBar progress=(ProgressBar) findViewById(R.id.progressBar);

        Log.d(TAG, "Initializing app");
        if (!SharedPrefUtil.areCategoriesInCache(getBaseContext())) {
            progress.setProgress(5);
            progressText.setText(getString(R.string.server_dialog));
            ServerHandler server = new ServerHandler(this);
            server.getCategories(new ResponseHandler() {
                @Override
                public void onSuccess(Object datas) {
                    progress.setProgress(30);
                    progressText.setText(getString(R.string.storing_data));
                    List<Category> categories = JsonUtil.parseJsonDatas((JSONArray) datas, Category.class);
                    DataAccess dataAccess = new DataAccess(getBaseContext());
                    dataAccess.open();
                    for (int i = 0; i < categories.size(); i++) {
                        dataAccess.createData(categories.get(i));
                        progressText.setText(getString(R.string.storing_data) + " " + (i + 1) + "/" + categories.size());
                        progress.setProgress(30 + (i + 1) * (70 / categories.size()));
                    }
                    dataAccess.close();
                }

                @Override
                public void onError(String error) {
                    Log.e(TAG, error);
                    Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
        }

        Log.d(TAG, "App Initialized");

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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