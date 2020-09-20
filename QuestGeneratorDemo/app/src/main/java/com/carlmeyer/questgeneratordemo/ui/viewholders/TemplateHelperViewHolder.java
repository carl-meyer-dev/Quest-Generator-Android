package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;

public class TemplateHelperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView tvTemplateHelper;
    TemplateHelperViewHolder.OnTemplateHelperListener onTemplateHelperListener;


    public TemplateHelperViewHolder(@NonNull View itemView, TemplateHelperViewHolder.OnTemplateHelperListener onTemplateHelperListener) {
        super(itemView);
        tvTemplateHelper = itemView.findViewById(R.id.tvTemplateHelper);
        this.onTemplateHelperListener = onTemplateHelperListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onTemplateHelperListener.onTemplateHelperClick(getAdapterPosition());
    }

    public interface OnTemplateHelperListener{
        void onTemplateHelperClick(int position);
    }
}
