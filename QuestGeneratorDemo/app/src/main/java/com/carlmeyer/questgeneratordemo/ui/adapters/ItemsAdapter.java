package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.ui.viewholders.ItemViewHolder;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class ItemsAdapter extends RealmRecyclerViewAdapter<Item, ItemViewHolder> {

    private ItemViewHolder.OnItemListener onItemListener;

    public ItemsAdapter(OrderedRealmCollection<Item> data, ItemViewHolder.OnItemListener onItemListener) {
        super(data, true);
        setHasStableIds(true);
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_item, parent, false);
        return new ItemViewHolder(itemView, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Item item = getItem(position);
        holder.itemData = item;
        //noinspection ConstantConditions
        final int itemID = item.getId();
        holder.tvItem.setText(item.getName());

    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }
}
