package com.shentuo.bakingapp.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.ActivityRecipeDetailBinding;
import com.shentuo.bakingapp.global.Constants;
import com.shentuo.bakingapp.model.Recipe;
import com.shentuo.bakingapp.model.Step;
import com.shentuo.bakingapp.ui.utils.Util;

/**
 * Created by ShentuoZhan on 8/5/17.
 */

public class RecipeDetailActivity extends AppCompatActivity implements StepListAdapter.ListItemClickListener {
    private final String TAG = RecipeDetailActivity.class.getSimpleName();
    private Recipe recipe;
    private ActivityRecipeDetailBinding binding;
    private boolean mTwoPane;
    private StepListAdapter mAdapter;
    private RecyclerView mStepsList;
    private StepDetailFragment detailFragment = new StepDetailFragment();
    private int currentStepId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int recipeId = getIntent().getIntExtra(Constants.RECIPE_ITEM_ID, Constants.INVALID_ID);
        recipe = Util.getRecipe(recipeId);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        mStepsList = binding.stepListFragment.rvSteps;

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mStepsList.setLayoutManager(layoutManager);

        mAdapter = new StepListAdapter(this);
        mStepsList.setAdapter(mAdapter);

        for (Step step : recipe.getSteps()) {
            mAdapter.addStepItem(step);
        }
        mAdapter.notifyDataSetChanged();

        if (binding.stepDetailContainer != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                currentStepId = recipe.getSteps().get(0).getId();
            } else {
                currentStepId = savedInstanceState.getInt(Constants.STEP_ITEM_ID);
            }
            detailFragment.setStep(Util.getStep(currentStepId, recipe));
            // Add the fragment to its container using a transaction
            getFragmentManager().beginTransaction()
                    .replace(binding.stepDetailContainer.getId(), detailFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants.STEP_ITEM_ID, currentStepId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(Step step) {
        if (mTwoPane) {
            detailFragment.setStep(step);
            detailFragment.updateContent();
        } else {
            Intent startDetailIntent = new Intent(this, StepDetailActivity.class);
            startDetailIntent.putExtra(Constants.RECIPE_ITEM_ID, recipe.getId());
            startDetailIntent.putExtra(Constants.STEP_ITEM_ID, step.getId());
            startActivity(startDetailIntent);
        }
    }
}
