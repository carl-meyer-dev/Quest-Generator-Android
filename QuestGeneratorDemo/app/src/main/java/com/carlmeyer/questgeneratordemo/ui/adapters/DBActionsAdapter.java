package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
import com.carlmeyer.questgeneratordemo.ui.interfaces.StartDragListener;
import com.carlmeyer.questgeneratordemo.ui.interfaces.StopDragListener;
import com.carlmeyer.questgeneratordemo.ui.utils.ItemMoveCallback;
import com.carlmeyer.questgeneratordemo.ui.viewholders.DBActionViewHolder;

import java.util.Collections;
import java.util.List;

public class DBActionsAdapter extends RecyclerView.Adapter<DBActionViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {

    private DBActionViewHolder.OnActionListener onActionListener;
    private List<DBAction> actions;
    private final StartDragListener mStartDragListener;
    private final StopDragListener mStopDragListener;


    public DBActionsAdapter(List<DBAction> data, DBActionViewHolder.OnActionListener onActionListener, StartDragListener startDragListener, StopDragListener stopDragListener) {
        this.onActionListener = onActionListener;
        mStartDragListener = startDragListener;
        mStopDragListener = stopDragListener;
        this.actions = data;
    }

    @NonNull
    @Override
    public DBActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_db_action, parent, false);
        return new DBActionViewHolder(itemView, onActionListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull DBActionViewHolder holder, int position) {
        final DBAction action = actions.get(position);
        holder.ivDragAndDropHandle.setOnTouchListener((v, event) -> {
            if (event.getAction() ==
                    MotionEvent.ACTION_DOWN) {
                mStartDragListener.requestDrag(holder);
            }
            return false;
        });
        holder.tvAction.setText(action.getAction());
        holder.tvActionConfig.setText(action.getConfig());
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }


    public DBAction getItem(int position) {
        return actions.get(position);
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(actions, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(actions, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(DBActionViewHolder myViewHolder) {
    }

    @Override
    public void onRowClear(DBActionViewHolder myViewHolder) {
        mStopDragListener.stopDrag(myViewHolder);
    }


}
