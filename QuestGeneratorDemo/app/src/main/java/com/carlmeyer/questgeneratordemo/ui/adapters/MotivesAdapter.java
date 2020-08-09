package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.ui.viewholders.MotivationViewHolder;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class MotivesAdapter extends RealmRecyclerViewAdapter<Motivation, MotivationViewHolder> {

    private MotivationViewHolder.OnMotivationListener onMotivationListener;

    public MotivesAdapter(OrderedRealmCollection<Motivation> data, MotivationViewHolder.OnMotivationListener onMotivationListener) {
        super(data, true);
        setHasStableIds(true);
        this.onMotivationListener = onMotivationListener;
    }

    @NonNull
    @Override
    public MotivationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_motivation, parent, false);
        return new MotivationViewHolder(itemView, onMotivationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MotivationViewHolder holder, int position) {
        final Motivation motivation = getItem(position);
        holder.motivationData = motivation;
        //noinspection ConstantConditions
        final int MotivationID = motivation.getId();
        holder.tvMotivation.setText(motivation.getMotivation());

    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }
}
