package com.example.clement.emm_project2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.server.ResponseHandler;
import com.example.clement.emm_project2.app.server.ServerHandler;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.util.ImageLoader;
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class FormationActivity extends AppCompatActivity {

    private final static String TAG = FormationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final String eanCode= extras.getString("ean");
            Log.wtf(TAG,"EAN => "+eanCode.toString());
            ServerHandler server = new ServerHandler(App.getAppContext());
            server.getFormation(eanCode, new ResponseHandler() {
                @Override
                public void onSuccess(Object datas) {
                    Formation formation = JsonUtil.parseJsonData((JSONObject) datas, Formation.class);
                    displayFormation(formation);
                }

                @Override
                public void onError(String error) {
                    Log.wtf(TAG, error);
                }
            });
        }
    }

    private void displayFormation(Formation formation){
        int loader = R.drawable.logo; // Change this to loader img :)
        TextView title = (TextView) findViewById(R.id.formationTitle);
        TextView subTitle = (TextView) findViewById(R.id.formationSubtitle);
        ImageView imageView = (ImageView) findViewById(R.id.formationPoster);
        TextView duration = (TextView) findViewById(R.id.formationDuration);
        TextView price = (TextView) findViewById(R.id.formationPrice);
        TextView lessonCount = (TextView) findViewById(R.id.lessonCount);



        ImageLoader imgLoader = new ImageLoader(App.getAppContext());
        imgLoader.DisplayImage(formation.getPoster(), loader, imageView);

        title.setText(formation.getTitle());
        subTitle.setText(formation.getSubtitle());
        duration.setText(StringUtil.formatDuration(formation.getDuration()));
        price.setText(formation.getPrice() + " €");
        lessonCount.setText(formation.getLessonNumber()+ " Leçons");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formation, menu);
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
