package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;

public class NPCViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvNPC;
    public NPC npcData;
    NPCViewHolder.OnNPCListener onNPCListener;

    public NPCViewHolder(@NonNull View itemView, NPCViewHolder.OnNPCListener onNPCListener) {
        super(itemView);
        tvNPC = itemView.findViewById(R.id.tvNPC);
        this.onNPCListener = onNPCListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onNPCListener.onNPCClick(getAdapterPosition());
    }

    public interface OnNPCListener{
        void onNPCClick(int position);
    }

}
