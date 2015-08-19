package com.example.clement.emm_project2.activities;

import android.app.Activity;
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
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import org.json.JSONArray;

import java.util.List;

public class SplashScreenActivity extends Activity {
    private final String TAG = SplashScreenActivity.class.getSimpleName();

    private class AsyncDataSynchronization extends AsyncTask<Void, Integer, Integer> {
        private final String TAG = AsyncDataSynchronization.class.getSimpleName();
        final ServerHandler server = new ServerHandler(SplashScreenActivity.this);
        ProgressBar progressBar;
        TextView progressText;

        public AsyncDataSynchronization(ProgressBar progressBar, TextView progressText) {
            this.progressBar = progressBar;
            this.progressText = progressText;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //UI Thread
            Log.i(TAG, "No data in cache, getting categories...");
            progressBar.setProgress(5);
            progressText.setText(getString(R.string.server_dialog));
        }

        @Override
        protected Integer doInBackground(Void... params) {

            server.getCategories(new ResponseHandler() {
                @Override
                public void onSuccess(Object datas) {
                    Log.i(TAG, "Got categories, registering in database...");

                    publishProgress(10);

                    List<Category> categories = JsonUtil.parseJsonDatas((JSONArray) datas, Category.class);

                    final DataAccess dataAccess = new DataAccess(getBaseContext());
                    for (int i = 0; i < categories.size(); i++) {
                        Category category = categories.get(i);
                        dataAccess.createData(category);

                        final List<SubCategory> subCategories = category.getSubCategoriesAsList();
                        for (final SubCategory subCategory : subCategories) {
                            subCategory.setCatId(category.getMongoID());
                            dataAccess.createData(subCategory);

                            server.getFormations(subCategory.getMongoID(), new ResponseHandler() {
                                @Override
                                public void onSuccess(Object datas) {
                                    Log.i(TAG, "Got Formation, registering in database...");
                                    List<Formation> formations = JsonUtil.parseJsonDatas((JSONArray) datas, Formation.class);
                                    for (int k = 0; k < formations.size(); k++) {
                                        Formation formation = formations.get(k);
                                        formation.setSubCatId(subCategory.getMongoID()); 
                                        dataAccess.createData(formation);
                                    }

                                }

                                @Override
                                public void onError(String error) {
                                    Log.wtf(TAG, error);
                                }
                            });
                        }

                        final int j = i;
                        publishProgress(0 + (j + 1) * (90 / categories.size()));

                    }
                    Log.i(TAG, "App initialized !");
                    progressText.setText(getString(R.string.application_ready));
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(String error) {
                    Log.wtf(TAG, error);
                    Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
            return 0;
        }

        protected void onProgressUpdate(Integer... progress) {
            this.progressBar.setProgress(progress[0]);
            progressText.setText(getString(R.string.server_dialog) + " " + this.progressBar.getProgress() + "%");
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView progressText = (TextView) findViewById(R.id.progressText);
        ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.upgradeDbIfNecessary();

        Intent intent = new Intent(this, MainActivity.class);

        Log.i(TAG, "Initializing app");
        if (!SharedPrefUtil.areCategoriesInCache()) {
            new AsyncDataSynchronization(progress, progressText).execute();
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
