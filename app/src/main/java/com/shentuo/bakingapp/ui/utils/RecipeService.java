package com.shentuo.bakingapp.ui.utils;

import com.shentuo.bakingapp.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ShentuoZhan on 9/5/17.
 */

public interface RecipeService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<ArrayList<Recipe>> getRecipeList();
}
