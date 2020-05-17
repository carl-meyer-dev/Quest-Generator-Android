package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;

import io.realm.Realm;

public class LocationsFragment extends Fragment {

    private Realm realm;
    private RecyclerView rvLocations;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_locations, container, false);
        rvLocations = root.findViewById(R.id.rvLocations);

        return root;
    }

    private void setUpRecyclerView(){
        layoutManager = new LinearLayoutManager(getContext());
        rvLocations.setLayoutManager(layoutManager);
    }
}
