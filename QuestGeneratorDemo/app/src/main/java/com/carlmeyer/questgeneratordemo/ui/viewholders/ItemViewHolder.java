package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView tvItem;
    public Item itemData;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        tvItem = itemView.findViewById(R.id.tvItem);
    }

}
