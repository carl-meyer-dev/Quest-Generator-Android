package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;

public class NPCViewHolder extends RecyclerView.ViewHolder {

    public TextView tvNPC;
    public NPC npcData;

    public NPCViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNPC = itemView.findViewById(R.id.tvNPC);
    }

}
