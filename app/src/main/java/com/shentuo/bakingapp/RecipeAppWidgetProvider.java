package com.shentuo.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.shentuo.bakingapp.data.RecipeDB;
import com.shentuo.bakingapp.global.Constants;
import com.shentuo.bakingapp.model.BakingIngredient;
import com.shentuo.bakingapp.model.Recipe;
import com.shentuo.bakingapp.ui.MainActivity;
import com.shentuo.bakingapp.ui.RecipeDetailActivity;
import com.shentuo.bakingapp.ui.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidgetProvider extends AppWidgetProvider {
    private static int currentAppWidgetId;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int recipeId, int appWidgetId) {

        currentAppWidgetId = appWidgetId;
        // Get current width to decide on single recipe vs baking grid view
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;

        if (width < 300) {
            rv = getSingleRecipeRemoteView(context, recipeId);
        } else {
            rv = getBakingGridRemoteView(context);
        }
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the single recipe mode widget
     *
     * @param context  The context
     * @param recipeId The database plant Id for watering button functionality
     * @return The RemoteViews for the single recipe mode widget
     */
    private static RemoteViews getSingleRecipeRemoteView(Context context, int recipeId) {
        // Set the click handler to open the DetailActivity for recipe ID,
        // or the MainActivity if recipe ID is invalid
        Intent intent;
        Recipe recipe = Util.getRecipe(recipeId);
        if (recipe == null) {
            intent = new Intent(context, MainActivity.class);
        } else { // Set on click to open the corresponding detail activity
            intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra(Constants.RECIPE_ITEM_ID, recipeId);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        String ingredientsText = "";
        String recipeName = "";
        if (recipe != null) {
            // Update the recipe image
            Picasso.with(context)
                    .load(recipe.getImage())
                    .into(views, R.id.iv_item_bg, new int[]{currentAppWidgetId});

            recipeName = recipe.getName();
            if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0) {
                ingredientsText = context.getResources().getString(R.string.ingredients);
                for (BakingIngredient ingredient : recipe.getIngredients()) {
                    ingredientsText += " " + ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient() + ",";
                }
                ingredientsText = ingredientsText.substring(0, ingredientsText.length() - 1);
            }
        }

        views.setTextViewText(R.id.tv_item_name, recipeName);
        views.setTextViewText(R.id.tv_item_ingredients, ingredientsText);

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.iv_item_bg, pendingIntent);

        return views;
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the GridView mode widget
     *
     * @param context The context
     * @return The RemoteViews for the GridView mode widget
     */
    private static RemoteViews getBakingGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        // Set the RecipeDetailActivity intent to launch when clicked
        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
        // Handle empty baking
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        List<Recipe> recipes = RecipeDB.getRecipeList();
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, recipes.get(i % recipes.size()).getId(), appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

