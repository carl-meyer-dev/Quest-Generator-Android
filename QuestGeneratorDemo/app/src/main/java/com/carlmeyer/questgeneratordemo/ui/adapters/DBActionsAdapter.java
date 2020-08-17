package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
import com.carlmeyer.questgeneratordemo.ui.viewholders.ActionViewHolder;

import java.util.List;

public class DBActionsAdapter extends RecyclerView.Adapter<ActionViewHolder> {

    private ActionViewHolder.OnActionListener onActionListener;
    private List<DBAction> actions;

    public DBActionsAdapter(List<DBAction> data, ActionViewHolder.OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
        this.actions = data;
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_db_action, parent, false);
        return new ActionViewHolder(itemView, onActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
        final DBAction action = actions.get(position);
        holder.tvAction.setText(action.getAction());
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }


    public DBAction getItem(int position) {
        return actions.get(position);
    }
}
