package com.example.clement.emm_project2.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.drawer.DrawerActivity;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.util.ImageLoader;
import com.example.clement.emm_project2.util.SharedPrefUtil;
import com.example.clement.emm_project2.util.StringUtil;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormationSummaryActivity extends DrawerActivity {

    private final static String TAG = FormationSummaryActivity.class.getSimpleName();
    private Formation formation;
    private Switch favoriteSwitch;
    private SharedPrefUtil sharedPref = new SharedPrefUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addFavoritesAndUserTravel();
        super.addCategories();

        favoriteSwitch = (Switch) findViewById(R.id.act_formation_switch);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final String eanCode= extras.getString("ean");

            if(sharedPref.isFormationFavorited(eanCode)){
                favoriteSwitch.setChecked(true);
            }

            favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sharedPref.addFavoriteFormation(eanCode);
                    } else {
                        sharedPref.removeFavoriteFormation(eanCode);
                    }
                }
            });

            DataAccess da = new DataAccess(App.getAppContext());
            List<Formation> formations = da.findDataWhere(Formation.class, "ean", eanCode);
            Formation formation = formations.get(0);
            getSupportActionBar().setTitle(formation.getTitle());
            displayFormation(formation);
        }
    }

    @Override
    protected void resetTitle(){
        setTitle(formation.getTitle());
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
        ratingCount.setText("(" + Math.round(ratingCountFloat) + " " + getString(R.string.rate) + ")");

        imgLoader.DisplayImage(formation.getAuthors().get(0).getPictureSmall(), loader, authorPicture);
        authorInfo.setText(getString(R.string.yourTeacher) + " " + formation.getTitle());
        authorName.setText(formation.getAuthors().get(0).getFullName());



        ImageView teaserPoster = (ImageView) findViewById(R.id.formationTeaserPoster);
        imgLoader.DisplayImage(formation.getTeaserInfo().get("video_poster"), loader, teaserPoster);

        ((ParallaxScrollView)findViewById(R.id.mainScrollView)).fullScroll(ScrollView.FOCUS_UP);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formation, menu);
        return true;
    }

    public void playTeaserFullScreen(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW );
        intent.setDataAndType(Uri.parse("http://eas.elephorm.com/videos/" + formation.getTeaser()), "video/*");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_user_preferences) {
            SharedPrefUtil.handleUserPreferencesDelete(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_formation;
    }
}
