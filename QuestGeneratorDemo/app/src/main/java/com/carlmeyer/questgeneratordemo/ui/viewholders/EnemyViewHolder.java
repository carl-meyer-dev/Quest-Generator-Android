package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;

public class EnemyViewHolder extends RecyclerView.ViewHolder {

    public TextView tvEnemy;
    public Enemy enemyData;

    public EnemyViewHolder(@NonNull View itemView) {
        super(itemView);
        tvEnemy = itemView.findViewById(R.id.tvEnemy);
    }

}
