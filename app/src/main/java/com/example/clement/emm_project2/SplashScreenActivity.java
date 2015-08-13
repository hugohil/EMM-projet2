package com.example.clement.emm_project2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

// TODO : remove ActionBarActivity and extend normal Activity
public class SplashScreenActivity extends ActionBarActivity {


    private final String TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView progressText = (TextView) findViewById(R.id.progressText);
        ProgressBar progress=(ProgressBar) findViewById(R.id.progressBar);

        Log.d(TAG, "Initializing app");


        // TODO here: Load datas, initialize app... etc
        for(int i = 1; i <=100 ; i++) {
            progress.setProgress(i);
            progressText.setText(i + " %");
        }

        Log.d(TAG, "App Initialized");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
