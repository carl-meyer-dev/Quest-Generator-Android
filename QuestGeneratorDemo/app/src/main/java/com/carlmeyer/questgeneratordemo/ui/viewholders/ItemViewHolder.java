package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvItem;
    public Item itemData;
    OnItemListener onItemListener;

    public ItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
        super(itemView);
        tvItem = itemView.findViewById(R.id.tvItem);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition());
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}
