package com.shentuo.bakingapp.ui.utils;

import com.shentuo.bakingapp.data.RecipeDB;
import com.shentuo.bakingapp.global.Constants;
import com.shentuo.bakingapp.model.Recipe;

/**
 * Created by ShentuoZhan on 11/5/17.
 */

public class Util {
    public static Recipe getRecipe(int recipeId) {
        if (RecipeDB.getRecipeList() == null || RecipeDB.getRecipeList().size() == 0 || recipeId == Constants.INVALID_RECIPE_ID) {
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
}
