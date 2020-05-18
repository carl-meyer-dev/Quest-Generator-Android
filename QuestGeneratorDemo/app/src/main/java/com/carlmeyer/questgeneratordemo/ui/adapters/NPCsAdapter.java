package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;
import com.carlmeyer.questgeneratordemo.ui.viewholders.NPCViewHolder;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class NPCsAdapter extends RealmRecyclerViewAdapter<NPC, NPCViewHolder> {

    public NPCsAdapter(OrderedRealmCollection<NPC> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public NPCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_npc, parent, false);
        return new NPCViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NPCViewHolder holder, int position) {
        final NPC npc = getItem(position);
        holder.npcData = npc;
        //noinspection ConstantConditions
        final int NPCID = npc.getId();
        holder.tvNPC.setText(npc.getName());

    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }
}
