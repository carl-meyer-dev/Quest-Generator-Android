package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;

public class StoryFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvStoryFragment;
    public StoryFragment storyFragmentData;
    OnStoryFragmentListener onStoryFragmentListener;


    public StoryFragmentViewHolder(@NonNull View itemView, OnStoryFragmentListener onStoryFragmentListener) {
        super(itemView);
        tvStoryFragment = itemView.findViewById(R.id.tvStoryFragment);
        this.onStoryFragmentListener = onStoryFragmentListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onStoryFragmentListener.onStoryFragmentClick(getAdapterPosition());
    }

    public interface OnStoryFragmentListener{
        void onStoryFragmentClick(int position);
    }

}
