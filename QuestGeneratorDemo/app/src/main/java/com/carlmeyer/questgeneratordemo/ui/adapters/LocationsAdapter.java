package com.carlmeyer.questgeneratordemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.ui.viewholders.LocationViewHolder;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class LocationsAdapter extends RealmRecyclerViewAdapter<Location, LocationViewHolder> {

    private LocationViewHolder.OnLocationListener onLocationListener;

    public LocationsAdapter(OrderedRealmCollection<Location> data, LocationViewHolder.OnLocationListener onLocationListener) {
        super(data, true);
        setHasStableIds(true);
        this.onLocationListener = onLocationListener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_location, parent, false);
        return new LocationViewHolder(itemView, onLocationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        final Location location = getItem(position);
        holder.locationData = location;
        //noinspection ConstantConditions
        final int locationID = location.getId();
        holder.tvLocation.setText(location.getName());


    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }
}
