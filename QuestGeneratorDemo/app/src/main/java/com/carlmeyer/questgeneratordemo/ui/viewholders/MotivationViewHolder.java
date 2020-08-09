package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;

public class MotivationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvMotivation;
    public Motivation motivationData;
    MotivationViewHolder.OnMotivationListener onMotivationListener;


    public MotivationViewHolder(@NonNull View itemView, OnMotivationListener onMotivationListener) {
        super(itemView);
        tvMotivation = itemView.findViewById(R.id.tvMotivation);
        this.onMotivationListener = onMotivationListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMotivationListener.onMotivationClick(getAdapterPosition());
    }

    public interface OnMotivationListener{
        void onMotivationClick(int position);
    }
}
