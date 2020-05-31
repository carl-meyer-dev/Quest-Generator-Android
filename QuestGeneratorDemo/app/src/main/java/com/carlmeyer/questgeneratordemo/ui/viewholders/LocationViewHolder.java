package com.carlmeyer.questgeneratordemo.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;

public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvLocation;
    public Location locationData;
    OnLocationListener onLocationListener;


    public LocationViewHolder(@NonNull View itemView, OnLocationListener onLocationListener) {
        super(itemView);
        tvLocation = itemView.findViewById(R.id.tvLocation);
        this.onLocationListener = onLocationListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onLocationListener.onLocationClick(getAdapterPosition());
    }

    public interface OnLocationListener{
        void onLocationClick(int position);
    }

}
