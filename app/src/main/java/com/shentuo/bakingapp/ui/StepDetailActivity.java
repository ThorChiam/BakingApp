package com.shentuo.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.ActivityStepDetailBinding;
import com.shentuo.bakingapp.global.Constants;
import com.shentuo.bakingapp.model.Recipe;
import com.shentuo.bakingapp.ui.utils.Util;

/**
 * Created by ShentuoZhan on 11/5/17.
 */

public class StepDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStepDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        int recipeId = getIntent().getIntExtra(Constants.RECIPE_ITEM_ID, Constants.INVALID_ID);
        int stepId = getIntent().getIntExtra(Constants.STEP_ITEM_ID, Constants.INVALID_ID);
        Recipe recipe = Util.getRecipe(recipeId);
        stepDetailFragment.setStep(Util.getStep(stepId, recipe));
        getFragmentManager().beginTransaction()
                .add(binding.stepDetailContainer.getId(), stepDetailFragment)
                .commit();
    }
}
