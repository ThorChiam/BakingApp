package com.shentuo.bakingapp.ui;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.FragmentRecipeListBinding;

/**
 * Created by ShentuoZhan on 8/5/17.
 */

public class RecipeListFragment extends Fragment {
    FragmentRecipeListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);
        return binding.getRoot();
    }
}
