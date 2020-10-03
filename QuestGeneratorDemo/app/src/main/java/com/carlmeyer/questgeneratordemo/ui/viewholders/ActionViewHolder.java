package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;

public class ActionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvAction;
    public TextView tvStep;
    public TextView tvActionType;
    public Action actionData;
    OnActionListener onActionListener;

    public ActionViewHolder(@NonNull View itemView, OnActionListener onActionListener) {
        super(itemView);
        tvAction = itemView.findViewById(R.id.tvAction);
        tvStep = itemView.findViewById(R.id.tvStep);
        tvActionType = itemView.findViewById(R.id.tvActionType);
        tvActionType.setVisibility(View.INVISIBLE);
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
