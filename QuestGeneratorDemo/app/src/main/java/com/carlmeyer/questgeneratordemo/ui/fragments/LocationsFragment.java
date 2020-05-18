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
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.ui.adapters.LocationsAdapter;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class LocationsFragment extends Fragment {

    private Realm realm;
    private RecyclerView rvLocations;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_locations, container, false);
        rvLocations = root.findViewById(R.id.rvLocations);

        realm = Realm.getDefaultInstance();
        rvLocations = root.findViewById(R.id.rvLocations);
        setUpRecyclerView();

        return root;
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // get a list of all the locations in the DB
        OrderedRealmCollection<Location> locations = realm.where(Location.class).findAll();
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLocations.setLayoutManager(layoutManager);
        // Initialize and set locationsAdapter with list of locations
        LocationsAdapter locationsAdapter = new LocationsAdapter(locations);
        rvLocations.setAdapter(locationsAdapter);
    }

    /*
     * It is good practice to null the reference from the view to the adapter when it is no longer needed.
     * Because the <code>RealmRecyclerViewAdapter</code> registers itself as a <code>RealmResult.ChangeListener</code>
     * the view may still be reachable if anybody is still holding a reference to the <code>RealmResult>.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        rvLocations.setAdapter(null);
        realm.close();
    }
}
