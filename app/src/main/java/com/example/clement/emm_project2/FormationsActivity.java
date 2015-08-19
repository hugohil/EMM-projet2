package com.example.clement.emm_project2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.server.ResponseHandler;
import com.example.clement.emm_project2.server.ServerHandler;
import com.example.clement.emm_project2.util.SharedPrefUtil;

import java.util.List;

public class FormationsActivity extends ActionBarActivity {

    private static final String TAG = FormationsActivity.class.getSimpleName();
    private DataAccess dataAccess = new DataAccess(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String subCatId = extras.getString("subCatId");
            List<Formation> formations = dataAccess.findDataWhere(Formation.class, "subCatId", subCatId);
            Log.d(TAG, "Formations nb => "+ formations.size());

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
}
