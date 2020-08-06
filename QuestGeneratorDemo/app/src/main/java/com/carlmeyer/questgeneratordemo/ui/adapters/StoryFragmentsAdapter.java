package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;
import com.carlmeyer.questgeneratordemo.ui.viewholders.StoryFragmentViewHolder;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class StoryFragmentsAdapter extends RealmRecyclerViewAdapter<StoryFragment, StoryFragmentViewHolder> {

    private StoryFragmentViewHolder.OnStoryFragmentListener onStoryFragmentListener;

    public StoryFragmentsAdapter(OrderedRealmCollection<StoryFragment> data, StoryFragmentViewHolder.OnStoryFragmentListener onStoryFragmentListener) {
        super(data, true);
        setHasStableIds(true);
        this.onStoryFragmentListener = onStoryFragmentListener;
    }

    @NonNull
    @Override
    public StoryFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_story_fragment, parent, false);
        return new StoryFragmentViewHolder(itemView, onStoryFragmentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryFragmentViewHolder holder, int position) {
        final StoryFragment storyFragment = getItem(position);
        holder.storyFragmentData = storyFragment;
        //noinspection ConstantConditions
        final int storyFragmentID = storyFragment.getId();
        holder.tvStoryFragment.setText(storyFragment.getDescription());


    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }
}
