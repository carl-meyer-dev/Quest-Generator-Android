package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.ui.viewholders.TemplateHelperViewHolder;

import java.util.List;

public class TemplateHelperAdapter extends RecyclerView.Adapter<TemplateHelperViewHolder>{
    
    private TemplateHelperViewHolder.OnTemplateHelperListener onTemplateHelperListener;
    private List<String> templateHelpers;

    public TemplateHelperAdapter(List<String> data, TemplateHelperViewHolder.OnTemplateHelperListener onTemplateHelperListener) {
        this.onTemplateHelperListener = onTemplateHelperListener;
        this.templateHelpers = data;
    }

    @NonNull
    @Override
    public TemplateHelperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_template_helper, parent, false);
        return new TemplateHelperViewHolder(itemView, onTemplateHelperListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateHelperViewHolder holder, int position) {
        final String templateHelper = templateHelpers.get(position);
        holder.tvTemplateHelper.setText(templateHelper);
    }

    @Override
    public int getItemCount() {
        return templateHelpers.size();
    }


    public String getItem(int position) {
        return templateHelpers.get(position);
    }

}
