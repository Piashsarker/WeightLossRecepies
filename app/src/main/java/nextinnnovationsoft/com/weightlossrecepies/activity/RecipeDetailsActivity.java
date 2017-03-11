package nextinnnovationsoft.com.weightlossrecepies.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import nextinnnovationsoft.com.weightlossrecepies.LicenseFragment;
import nextinnnovationsoft.com.weightlossrecepies.R;

public class RecipeDetailsActivity extends AppCompatActivity {

    String name , description ;
    int imageId;
    TextView txtDetails, txtRecipeName;
    FloatingActionButton share ;
    private String tag ;
    Context context ;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        context = RecipeDetailsActivity.this;
        txtRecipeName = (TextView) findViewById(R.id.txt_recipe_name);

        txtDetails = (TextView) findViewById(R.id.txt_recipe_details);
        share = (FloatingActionButton) findViewById(R.id.btn_share);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");
        imageId = intent.getIntExtra("image",1);
        tag= intent.getStringExtra(RecipeCategoryOneActivity.TAG);

        collapsingToolbar.setTitle(name);
        txtDetails.setText(description);
        txtRecipeName.setText(name);

        loadBackdrop();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShare(name, description);
            }
        });
        loadAdMob();
        loadAdMobTwo();
        loadInterstitialAd();
    }

    private void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1714609736931391/8138930860");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });


        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void loadAdMob() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1714609736931391~8721660466");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    private void loadAdMobTwo() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1714609736931391~8721660466");
        AdView mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void openShare(String name , String description ) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        String appLink = "https://play.google.com/store/apps/details?id="+RecipeDetailsActivity.this.getPackageName();
        sharingIntent.setType("text/plain");
        String shareBodyText = name+"\n"+description+"\n"+appLink ;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Weight Loss Recipes Android App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

    private void openSubmitBug() {
        String to = "sarkerpt@gmail.com";
        String subject = "Weight Loss Recipes Light For Android - Bug Report";

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    private void openRate() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(imageId).centerCrop().into(imageView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                if(RecipeCategoryOneActivity.RECIPIE_CATEGORY_ONE_ACTIVITY.equals(tag)){
                    Intent intent = new Intent(RecipeDetailsActivity.this,RecipeCategoryOneActivity.class);
                    startActivity(intent);
                    break;
                }
                if(RecipeCategoryTwoActivity.RECIPIE_CATEGORY_TWO_ACTIVITY.equals(tag)){
                    Intent intent = new Intent(RecipeDetailsActivity.this,RecipeCategoryTwoActivity.class);
                    startActivity(intent);
                    break;
                }
                if(RecipeCategoryThreeActivity.RECIPIE_CATEGORY_THREE_ACTIVITY.equals(tag)){
                    Intent intent = new Intent(RecipeDetailsActivity.this,RecipeCategoryThreeActivity.class);
                    startActivity(intent);
                    break;
                }
                break;
            case R.id.item_share:
                openShare(name,description);
                break;
            case R.id.item_submit_bug:
                openSubmitBug();
                break;
            case R.id.item_rate_app:
                openRate();
                break;
            case R.id.item_license:
                openLisence();
                break;

        }
        return true;
    }
    private void openLisence() {
        LicenseFragment licensesFragment = new LicenseFragment();
        licensesFragment.show(getSupportFragmentManager().beginTransaction(), "dialog_licenses");
    }
}
