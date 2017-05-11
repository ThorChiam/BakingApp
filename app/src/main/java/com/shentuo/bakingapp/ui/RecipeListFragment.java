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
import com.shentuo.bakingapp.ui.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ShentuoZhan on 8/5/17.
 */

public class RecipeListFragment extends Fragment implements RecipeListAdapter.ListItemClickListener {
    private final String TAG = "RecipeListFragment";
    private FragmentRecipeListBinding binding;
    private RecipeListAdapter mAdapter;
    private RecyclerView mRecipesList;
    private ProgressBar mLoadingIndicator;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);

        mRecipesList = binding.rvRecipes;
        mLoadingIndicator = binding.pbLoadingIndicator;
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns());
        mRecipesList.setLayoutManager(layoutManager);

        mAdapter = new RecipeListAdapter(this);

        mRecipesList.setAdapter(mAdapter);

        if (NetworkUtils.isOnline(getActivity())) {
            getRecipeListFromServer();
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

    private void getRecipeListFromServer() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        disposables.add(
                NetworkUtils.getRecipeList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ArrayList<Recipe>>() {
                            @Override
                            public void onNext(ArrayList<Recipe> recipeList) {
                                for (Recipe recipe : recipeList) {
                                    mAdapter.addRecipeItem(recipe);
                                }
                                mAdapter.notifyDataSetChanged();
                                mLoadingIndicator.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                mLoadingIndicator.setVisibility(View.GONE);
                                NetworkUtils.showErrorMessage(binding.getRoot().getContext(), e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                mLoadingIndicator.setVisibility(View.GONE);
                            }
                        })
        );
    }

    @Override
    public void onListItemClick(Recipe recipe) {
        Intent startDetailIntent = new Intent(getActivity(), RecipeDetailActivity.class);
        startDetailIntent.putExtra(Constants.RECIPE_ITEM, recipe);
        startActivity(startDetailIntent);
    }
}
