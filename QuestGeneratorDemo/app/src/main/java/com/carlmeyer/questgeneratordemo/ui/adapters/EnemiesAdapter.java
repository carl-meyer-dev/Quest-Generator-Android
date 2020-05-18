package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.ui.viewholders.EnemyViewHolder;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class EnemiesAdapter extends RealmRecyclerViewAdapter<Enemy, EnemyViewHolder> {

    public EnemiesAdapter(OrderedRealmCollection<Enemy> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public EnemyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_enemy, parent, false);
        return new EnemyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EnemyViewHolder holder, int position) {
        final Enemy enemy = getItem(position);
        //noinspection ConstantConditions
        final int EnemyID = enemy.getId();
        holder.tvEnemy.setText(enemy.getName());

    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }
}
