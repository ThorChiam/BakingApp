package com.shentuo.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.shentuo.bakingapp.data.RecipeDB;
import com.shentuo.bakingapp.global.Constants;
import com.shentuo.bakingapp.model.BakingIngredient;
import com.shentuo.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ShentuoZhan on 11/5/17.
 */

public class GridWidgetService extends RemoteViewsService {
    private int appWidgetId;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    private class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        List<Recipe> mRecipes;

        public GridRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDestroy() {

        }

        //called on start and when notifyAppWidgetViewDataChanged is called
        @Override
        public void onDataSetChanged() {
            // Get all plant info ordered by creation time
            mRecipes = RecipeDB.getRecipeList();
        }

        @Override
        public int getCount() {
            if (mRecipes == null) return 0;
            return mRecipes.size();
        }

        /**
         * This method acts like the onBindViewHolder method in an Adapter
         *
         * @param position The current position of the item in the GridView to be displayed
         * @return The RemoteViews object to display for the provided postion
         */
        @Override
        public RemoteViews getViewAt(int position) {
            if (mRecipes == null || mRecipes.size() == 0) return null;
            Recipe recipe = mRecipes.get(position);

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);
            String ingredientsText = "";
            String recipeName = "";
            Intent fillInIntent = new Intent();
            if (recipe != null) {
                // Update the recipe image
                Picasso.with(mContext)
                        .load(recipe.getImage())
                        .into(views, R.id.iv_item_bg, new int[]{appWidgetId});

                recipeName = recipe.getName();
                if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0) {
                    ingredientsText = mContext.getResources().getString(R.string.ingredients);
                    for (BakingIngredient ingredient : recipe.getIngredients()) {
                        ingredientsText += " " + ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient() + ",";
                    }
                    ingredientsText = ingredientsText.substring(0, ingredientsText.length() - 1);
                }

                fillInIntent.putExtra(Constants.RECIPE_ITEM_ID, recipe.getId());
            }

            views.setTextViewText(R.id.tv_item_name, recipeName);
            views.setTextViewText(R.id.tv_item_ingredients, ingredientsText);

            // Fill in the onClick PendingIntent Template using the specific recipe Id for each item individually
            views.setOnClickFillInIntent(R.id.iv_item_bg, fillInIntent);

            return views;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1; // Treat all items in the GridView the same
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}


