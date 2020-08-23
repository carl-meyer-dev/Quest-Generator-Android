package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;

public class DBActionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvAction;
    public TextView tvStep;
    public TextView tvActionType;
    public Action actionData;
    public View rowView;
    public ImageView ivDragAndDropHandle;

    OnActionListener onActionListener;

    public DBActionViewHolder(@NonNull View itemView, OnActionListener onActionListener) {
        super(itemView);
        rowView = itemView;
        tvAction = itemView.findViewById(R.id.tvAction);
        tvStep = itemView.findViewById(R.id.tvStep);
        tvActionType = itemView.findViewById(R.id.tvActionType);
        ivDragAndDropHandle = itemView.findViewById(R.id.ivDragAndDrop);
        this.onActionListener = onActionListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onActionListener.onActionClick(getAdapterPosition());
    }

    public interface OnActionListener {
        void onActionClick(int position);
    }
}
