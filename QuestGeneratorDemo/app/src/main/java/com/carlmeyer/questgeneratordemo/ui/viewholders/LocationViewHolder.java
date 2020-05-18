package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;

public class LocationViewHolder extends RecyclerView.ViewHolder {

    public TextView tvLocation;
    public Location locationData;

    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);
        tvLocation = itemView.findViewById(R.id.tvLocation);
    }

}
