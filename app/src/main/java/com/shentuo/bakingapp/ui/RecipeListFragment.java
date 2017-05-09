package com.shentuo.bakingapp.ui;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.FragmentRecipeListBinding;
import com.shentuo.bakingapp.global.Constants;
import com.shentuo.bakingapp.model.Recipe;

/**
 * Created by ShentuoZhan on 8/5/17.
 */

public class RecipeListFragment extends Fragment implements RecipeListAdapter.ListItemClickListener {
    FragmentRecipeListBinding binding;
    private RecipeListAdapter mAdapter;
    private RecyclerView mRecipesList;
    private ProgressBar mLoadingIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);

        mRecipesList = binding.rvRecipes;
        mLoadingIndicator = binding.pbLoadingIndicator;
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns());
        mRecipesList.setLayoutManager(layoutManager);

        mAdapter = new RecipeListAdapter(this);

        mRecipesList.setAdapter(mAdapter);

        for (int i = 0; i < 20; i++) {
            Recipe recipe = new Recipe();
            recipe.setId(i);
            recipe.setName("Recipe " + i);
            mAdapter.addRecipeItem(recipe);
        }
        return binding.getRoot();
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int minTabletWidth = (int) getResources().getDimension(R.dimen.min_tablet_width);
        return width >= minTabletWidth ? 3 : 1;
    }

    @Override
    public void onListItemClick(Recipe recipe) {
        Intent startDetailIntent = new Intent(getActivity(), RecipeDetailActivity.class);
        startDetailIntent.putExtra(Constants.EXTRA_KEY, recipe);
        startActivity(startDetailIntent);
    }
}
