package nextinnnovationsoft.com.weightlossrecepies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import nextinnnovationsoft.com.weightlossrecepies.R;
import nextinnnovationsoft.com.weightlossrecepies.Recipe;
import nextinnnovationsoft.com.weightlossrecepies.WeightLossRecipesDataSource;
import nextinnnovationsoft.com.weightlossrecepies.adapter.RecipesCategoryAdapter;

public class RecipeCategoryTwoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private RecipesCategoryAdapter mAdapter;
    private Intent intent;
    public static final String RECIPIE_CATEGORY_TWO_ACTIVITY = "RecipeCategoryTwoActivity";
    private List<Recipe> recipeList ;
    public static final String TAG = "tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_category_two);
        intent =getIntent();
        recipeList = new ArrayList<>();
        recipeList = WeightLossRecipesDataSource.getRecipeCategoryTwoList();
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
        final List<Recipe> data = recipeList;

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mAdapter = new RecipesCategoryAdapter(this, data,RECIPIE_CATEGORY_TWO_ACTIVITY);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        mAdapter.setOnItemClickListener(new RecipesCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                intent = new Intent(RecipeCategoryTwoActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("name",recipeList.get(position).getRecipeName());
                intent.putExtra("image",recipeList.get(position).getRecipeImage());
                intent.putExtra("description",recipeList.get(position).getDescription());
                intent.putExtra(TAG,RECIPIE_CATEGORY_TWO_ACTIVITY);
                startActivity(intent);
            }
        });
    }

}
