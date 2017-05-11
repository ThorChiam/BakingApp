package com.shentuo.bakingapp.ui;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.FragmentStepDetailBinding;
import com.shentuo.bakingapp.model.Step;

/**
 * Created by ShentuoZhan on 8/5/17.
 */

public class StepDetailFragment extends Fragment {
    private FragmentStepDetailBinding binding;
    private Step step;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        updateContent();
        return binding.getRoot();
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public void updateContent() {
        binding.tvStepDescription.setText(step.getDescription());
    }
}
