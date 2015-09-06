package com.example.clement.emm_project2.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.server.ResponseHandler;
import com.example.clement.emm_project2.app.server.ServerHandler;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.util.ImageLoader;
import com.example.clement.emm_project2.util.JsonUtil;
import com.example.clement.emm_project2.util.StringUtil;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormationSummaryActivity extends AppCompatActivity {

    private final static String TAG = FormationSummaryActivity.class.getSimpleName();
    private Formation formation;

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
                    getSupportActionBar().setTitle(formation.getTitle());
                    displayFormation(formation);
                }

                @Override
                public void onError(String error) {
                    Log.wtf(TAG, error);
                }
            });
        }


    }

    public void startFormation(View v) {
        Intent intent = new Intent(this, FormationDetailActivity.class);
        intent.putExtra("ean", formation.getEan());
        startActivity(intent);
    }

    private void displayFormation(Formation formation){
        this.formation = formation;
        int loader = R.drawable.loader;
        TextView title = (TextView) findViewById(R.id.formationTitle);
        TextView subTitle = (TextView) findViewById(R.id.formationSubtitle);
        ImageView imageView = (ImageView) findViewById(R.id.formationPoster);
        TextView duration = (TextView) findViewById(R.id.formationDuration);
        TextView price = (TextView) findViewById(R.id.formationPrice);
        TextView lessonCount = (TextView) findViewById(R.id.lessonCount);
        ExpandableTextView description = (ExpandableTextView) findViewById(R.id.formationDescription);
        RatingBar rating = (RatingBar) findViewById(R.id.formationRating);
        TextView ratingCount = (TextView) findViewById(R.id.formationRatingCount);
        CircleImageView authorPicture = (CircleImageView) findViewById(R.id.formationAuthorPicture);
        TextView authorInfo = (TextView) findViewById(R.id.formationAuthorInfo);
        TextView authorName = (TextView) findViewById(R.id.formationAuthorName);

        ImageLoader imgLoader = new ImageLoader(App.getAppContext());
        imgLoader.DisplayImage(formation.getPoster(), loader, imageView);
        title.setText(formation.getTitle());
        subTitle.setText(formation.getSubtitle());
        duration.setText(StringUtil.formatDuration(formation.getDuration()));
        price.setText(StringUtil.truncatePrice(formation.getPrice()) + " €");
        lessonCount.setText(formation.getLessonNumber() + " Leçons");
        description.setText(Html.fromHtml(formation.getDescription()));

        if(formation.getRating().get("average") != null) {
            Float averageRating = new Float(formation.getRating().get("average"));
            rating.setRating(averageRating / 20);
            rating.setStepSize(0.10f);
            rating.setIsIndicator(true);
        }
        Float ratingCountFloat = formation.getRating().get("count");
        if(ratingCountFloat == null){
            ratingCountFloat = 0.0f;
        }
        ratingCount.setText("("+Math.round(ratingCountFloat)+ " " + getString(R.string.rate) + ")");

        imgLoader.DisplayImage(formation.getAuthors().get(0).getPictureSmall(), loader, authorPicture);
        authorInfo.setText(getString(R.string.yourTeacher) + " "+ formation.getTitle());
        authorName.setText(formation.getAuthors().get(0).getFullName());


        VideoView videoView = (VideoView)findViewById(R.id.formationTeaser);
        videoView.setVideoURI(Uri.parse("http://eas.elephorm.com/videos/" + formation.getTeaser()));


        MediaController controller = new MediaController(this);
        controller.setAnchorView(videoView);
        controller.setMediaPlayer(videoView);
        videoView.setMediaController(controller);

        ((ParallaxScrollView)findViewById(R.id.mainScrollView)).fullScroll(ScrollView.FOCUS_UP);

        /*Intent intent = new Intent(Intent.ACTION_VIEW );
        intent.setDataAndType(Uri.parse("http://eas.elephorm.com/videos/" + formation.getTeaser()), "video*//*");
        startActivity(intent);*/

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
