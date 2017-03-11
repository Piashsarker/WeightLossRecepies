package nextinnnovationsoft.com.weightlossrecepies.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import nextinnnovationsoft.com.weightlossrecepies.R;
import nextinnnovationsoft.com.weightlossrecepies.Recipe;
import nextinnnovationsoft.com.weightlossrecepies.activity.MainActivity;
import nextinnnovationsoft.com.weightlossrecepies.activity.RecipeCategoryOneActivity;
import nextinnnovationsoft.com.weightlossrecepies.activity.RecipeCategoryThreeActivity;
import nextinnnovationsoft.com.weightlossrecepies.activity.RecipeCategoryTwoActivity;

/**
 * Created by PT on 3/3/2017.
 */
public class RecipesCategoryAdapter extends RecyclerView.Adapter<RecipesCategoryAdapter.ViewHolder> {

   private  Context mContext;
   private  OnItemClickListener mItemClickListener;
   private  List<Recipe> dataList = Collections.emptyList()  ;
    private String  TAG ;
    // 2
    public RecipesCategoryAdapter(Context context , List<Recipe> dataList, String tag ) {
        this.mContext = context;
        this.dataList = dataList;
        this.TAG = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(TAG ==RecipeCategoryOneActivity.RECIPIE_CATEGORY_ONE_ACTIVITY || TAG == RecipeCategoryTwoActivity.RECIPIE_CATEGORY_TWO_ACTIVITY || TAG == RecipeCategoryThreeActivity.RECIPIE_CATEGORY_THREE_ACTIVITY){
            holder.wieghtLoss.setText(dataList.get(position).getRecipeName());
        }
        if(TAG== MainActivity.TAG_MAIN_ACTIVITY){
            holder.placeName.setText(dataList.get(position).getRecipeName());
        }




        Picasso.with(mContext).load(dataList.get(position).getRecipeImage()).into(holder.placeImage);

        Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.easy_pork_chops_with_swee);

        Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                int bgColor = palette.getDominantColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
                holder.placeNameHolder.setBackgroundColor(bgColor);
            }
        });
        animate(holder);
    }
    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // 3
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout placeHolder;
        public RelativeLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;
        public TextView wieghtLoss ;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.recipe_category_name);
            placeNameHolder = (RelativeLayout) itemView.findViewById(R.id.recipe_category_holder);
            placeImage = (ImageView) itemView.findViewById(R.id.recipe_category_image);
            wieghtLoss = (TextView) itemView.findViewById(R.id.txt_weight_loss);
            placeHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}