package com.shentuo.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        getFragmentManager().beginTransaction()
                .add(binding.recipeListFragment.getId(), recipeListFragment)
                .commit();
    }
}
