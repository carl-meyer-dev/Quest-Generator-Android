package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.ui.viewholders.ActionViewHolder;

import java.util.List;

public class ActionsAdapter extends RecyclerView.Adapter<ActionViewHolder> {

    private ActionViewHolder.OnActionListener onActionListener;
    private List<Action> actions;

    public ActionsAdapter(List<Action> data, ActionViewHolder.OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
        this.actions = data;
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_action, parent, false);
        return new ActionViewHolder(itemView, onActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
        final Action action = actions.get(position);
        holder.actionData = action;
        holder.tvAction.setText(action.actionText);
        holder.tvStep.setText(String.valueOf(position + 1));
        holder.tvActionType.setText(action.actionType);
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }
}
