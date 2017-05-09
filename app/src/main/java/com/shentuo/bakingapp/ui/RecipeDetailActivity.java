package com.shentuo.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.ActivityRecipeDetailBinding;
import com.shentuo.bakingapp.global.Constants;
import com.shentuo.bakingapp.model.Recipe;

/**
 * Created by ShentuoZhan on 8/5/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {
    Recipe recipe;
    ActivityRecipeDetailBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        recipe = (Recipe) getIntent().getSerializableExtra(Constants.EXTRA_KEY);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
    }
}
