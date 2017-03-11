package nextinnnovationsoft.com.weightlossrecepies.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import nextinnnovationsoft.com.weightlossrecepies.LicenseFragment;
import nextinnnovationsoft.com.weightlossrecepies.R;
import nextinnnovationsoft.com.weightlossrecepies.Recipe;
import nextinnnovationsoft.com.weightlossrecepies.WeightLossRecipesDataSource;
import nextinnnovationsoft.com.weightlossrecepies.adapter.RecipesCategoryAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  final Context context = MainActivity.this;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private RecipesCategoryAdapter mAdapter;
    public static final String TAG_MAIN_ACTIVITY = "MainActivity";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadRecipesCategoryList();
        loadAdMob();

    }

    private void loadAdMob() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1714609736931391~8721660466");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void loadRecipesCategoryList() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        final List<Recipe> data = fillWithPlaceData();

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mAdapter = new RecipesCategoryAdapter(this, data , TAG_MAIN_ACTIVITY);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(100);
        itemAnimator.setRemoveDuration(100);
        mRecyclerView.setItemAnimator(itemAnimator);

        mAdapter.setOnItemClickListener(new RecipesCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0 :
                        Intent intent = new Intent(MainActivity.this, RecipeCategoryOneActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(MainActivity.this, RecipeCategoryTwoActivity.class);
                        startActivity(intent2);
                        break ;
                    case 2:
                       Intent intent3 = new Intent(MainActivity.this, RecipeCategoryThreeActivity.class);
                        startActivity(intent3);
                        break ;
                }
            }
        });
    }

    private List<Recipe> fillWithPlaceData() {
        return  WeightLossRecipesDataSource.getRecipeList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dinner) {
            Intent intent = new Intent(MainActivity.this, RecipeCategoryOneActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_smooth) {
            Intent intent = new Intent(MainActivity.this, RecipeCategoryTwoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_top_30) {
            Intent intent = new Intent(MainActivity.this, RecipeCategoryThreeActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_share){
            openShare();
        }
        else if(id==R.id.nav_send){
            openRate();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.item_share:
                openShare();
                break;
            case R.id.item_rate_app:
                openRate();
                break ;
            case R.id.item_submit_bug:
                openSubmitBug();
                break ;
            case R.id.item_license:
                openLisence();
                break;

        }

        return true;
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

    private void openShare() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        String appLink = "https://play.google.com/store/apps/details?id="+context.getPackageName();
        sharingIntent.setType("text/plain");
        String shareBodyText = "Check Out The Simple Weight Loss Recipes Android App. \n Link: "+appLink +" \n" +
                " #WeightLossRecipes #Android";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Weight Loss Recipes Android App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

    private void openLisence() {
        LicenseFragment licensesFragment = new LicenseFragment();
        licensesFragment.show(getSupportFragmentManager().beginTransaction(), "dialog_licenses");
    }
}
