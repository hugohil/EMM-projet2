package com.example.clement.emm_project2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clement.emm_project2.adapters.SubCatListAdapter;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.SubCategory;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class SubCatActivity extends ActionBarActivity {
    private ArrayList<SubCategory> subCats = new ArrayList<SubCategory>();
    private SubCatListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat);

        listView = (ListView) findViewById(R.id.act_subcat_list);
        adapter = new SubCatListAdapter(this, subCats);
        listView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            TextView desc = (TextView) findViewById(R.id.act_subcat_desc);
            desc.setText(extras.getString("desc"));
            try{
                ObjectMapper mapper = new ObjectMapper();
                subCats = (ArrayList<SubCategory>) mapper.readValue(extras.getString("subcat"), ArrayList.class);
                Log.d(SubCatActivity.class.getSimpleName(), ""+subCats.size());
                adapter.notifyDataSetChanged();

            } catch(Exception e){
                Log.e(SubCatActivity.class.getSimpleName(), e.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_cat, menu);
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
