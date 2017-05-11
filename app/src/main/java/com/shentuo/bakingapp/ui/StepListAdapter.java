package com.shentuo.bakingapp.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.StepListItemBinding;
import com.shentuo.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShentuoZhan on 8/5/17.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {
    private static final String TAG = StepListAdapter.class.getSimpleName();
    final private StepListAdapter.ListItemClickListener mOnClickListener;
    private Context context;
    private List<Step> mData;

    public interface ListItemClickListener {
        void onListItemClick(Step step);
    }

    public StepListAdapter(StepListAdapter.ListItemClickListener listener) {
        mOnClickListener = listener;
        mData = new ArrayList<>();
    }

    public void addStepItem(Step step) {
        mData.add(step);
    }

    public void clearItems() {
        mData = new ArrayList<>();
    }

    @Override
    public StepListAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        StepListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.step_list_item, parent, false);

        return new StepListAdapter.StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StepListAdapter.StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        StepListItemBinding binding;

        public StepViewHolder(StepListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
            binding.getRoot().setOnClickListener(this);
        }

        void bind(int listIndex) {
            Step step = mData.get(listIndex);
            if (step != null) {
                binding.tvItemShortDescription.setText(step.getShortDescription());
                if (step.getThumbnailURL() != null && !TextUtils.isEmpty(step.getThumbnailURL())) {
                    Picasso.with(context)
                            .load(step.getThumbnailURL())
                            .placeholder(R.color.colorCardBg)
                            .error(R.color.colorCardBg)
                            .into(binding.ivItemBg);
                }
            }
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (clickedPosition > -1 && clickedPosition < mData.size()) {
                mOnClickListener.onListItemClick(mData.get(clickedPosition));
            }
        }
    }
}
