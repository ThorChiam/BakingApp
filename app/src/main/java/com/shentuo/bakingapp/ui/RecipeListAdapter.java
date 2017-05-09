package com.shentuo.bakingapp.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shentuo.bakingapp.R;
import com.shentuo.bakingapp.databinding.RecipeListItemBinding;
import com.shentuo.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShentuoZhan on 8/5/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private static final String TAG = RecipeListAdapter.class.getSimpleName();
    final private ListItemClickListener mOnClickListener;
    private Context context;
    private List<Recipe> mData;

    public interface ListItemClickListener {
        void onListItemClick(Recipe recipe);
    }

    public RecipeListAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
        mData = new ArrayList<>();
    }

    public void addRecipeItem(Recipe recipe) {
        mData.add(recipe);
    }

    public void clearItems() {
        mData = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecipeListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecipeListItemBinding binding;

        public RecipeViewHolder(RecipeListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
            binding.getRoot().setOnClickListener(this);
        }

        void bind(int listIndex) {
            Recipe recipe = mData.get(listIndex);
            binding.tvItemRecipe.setText(recipe.getName());
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
