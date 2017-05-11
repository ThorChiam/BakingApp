package com.shentuo.bakingapp.global;

import android.support.multidex.MultiDexApplication;

import com.shentuo.bakingapp.data.RecipeDB;

/**
 * Created by ShentuoZhan on 11/5/17.
 */

public class BakingApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        RecipeDB.init(this);
    }
}
