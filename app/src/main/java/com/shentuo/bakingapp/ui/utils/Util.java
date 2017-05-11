package com.shentuo.bakingapp.ui.utils;

import com.shentuo.bakingapp.data.RecipeDB;
import com.shentuo.bakingapp.global.Constants;
import com.shentuo.bakingapp.model.Recipe;
import com.shentuo.bakingapp.model.Step;

/**
 * Created by ShentuoZhan on 11/5/17.
 */

public class Util {
    public static Recipe getRecipe(int recipeId) {
        if (RecipeDB.getRecipeList() == null || RecipeDB.getRecipeList().size() == 0 || recipeId == Constants.INVALID_ID) {
            return null;
        } else {
            for (Recipe recipe : RecipeDB.getRecipeList()) {
                if (recipeId == recipe.getId()) {
                    return recipe;
                }
            }
            return null;
        }
    }

    public static Step getStep(int stepId, Recipe recipe) {
        if (recipe == null || recipe.getSteps().size() == 0) {
            return null;
        } else {
            for (Step step : recipe.getSteps()) {
                if (stepId == step.getId()) {
                    return step;
                }
            }
            return null;
        }
    }
}
