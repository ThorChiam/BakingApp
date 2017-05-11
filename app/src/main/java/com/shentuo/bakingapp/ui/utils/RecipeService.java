package com.shentuo.bakingapp.ui.utils;

import com.shentuo.bakingapp.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ShentuoZhan on 9/5/17.
 */

public interface RecipeService {
    @GET("topher/2017/March/58d1537b_baking/baking.json")
    Observable<ArrayList<Recipe>> getRecipeList();
}
