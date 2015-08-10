package com.example.clement.emm_project2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.server.ResponseHandler;
import com.example.clement.emm_project2.server.ServerHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends ActionBarActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServerHandler server = new ServerHandler(this);
        // TODO: this is async so we need to add a loader or something

        server.getCategories(new ResponseHandler(){
            @Override
            public void onSuccess(Object datas){
                // Log.d(TAG, datas.toString());
                ObjectMapper mapper = new ObjectMapper();
                JSONArray json = (JSONArray) datas;
                try {
                    for (int i = 0; i < json.length(); i++){
                        Category cat = mapper.readValue(json.getJSONObject(i).toString(), Category.class);
                        categories.add(cat);
                    }
                    Log.d(TAG, "size: "+categories.size());
                } catch (Exception error){
                    Log.e(TAG, error.toString());
                }
            }
            @Override
            public void onError(String error){
                Log.e(TAG, error);
                Toast toast = Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
