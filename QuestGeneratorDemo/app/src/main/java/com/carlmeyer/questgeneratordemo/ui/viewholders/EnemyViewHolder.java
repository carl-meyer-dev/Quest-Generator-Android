package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;

public class EnemyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvEnemy;
    public Enemy enemyData;
    EnemyViewHolder.OnEnemyListener onEnemyListener;


    public EnemyViewHolder(@NonNull View itemView, OnEnemyListener onEnemyListener) {
        super(itemView);
        tvEnemy = itemView.findViewById(R.id.tvEnemy);
        this.onEnemyListener = onEnemyListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onEnemyListener.onEnemyClick(getAdapterPosition());
    }

    public interface OnEnemyListener{
        void onEnemyClick(int position);
    }
}
