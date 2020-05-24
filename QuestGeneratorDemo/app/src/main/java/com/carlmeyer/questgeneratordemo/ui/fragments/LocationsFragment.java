package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.ui.adapters.LocationsAdapter;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class LocationsFragment extends Fragment {

    private Realm realm;
    private RecyclerView rvLocations;
    private Button btnAddLocation;
    private OrderedRealmCollection<Location> locations;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_locations, container, false);
        rvLocations = root.findViewById(R.id.rvLocations);
        realm = Realm.getDefaultInstance();
        btnAddLocation = root.findViewById(R.id.btnAddLocation);
        rvLocations = root.findViewById(R.id.rvLocations);
        // set up UI onclick listeners etc...
        setUpUI();
        // set up recyclerview
        setUpRecyclerView();
        return root;
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // get a list of all the locations in the DB
        locations = realm.where(Location.class).findAll();
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLocations.setLayoutManager(layoutManager);
        // Initialize and set locationsAdapter with list of locations
        LocationsAdapter locationsAdapter = new LocationsAdapter(locations);
        rvLocations.setAdapter(locationsAdapter);
    }

    /**
     * Set up UI onclick listeners etc
     */
    private void setUpUI(){
        btnAddLocation.setOnClickListener(v -> showAddLocationDialog());
    }

    /**
     * Setup, Configure and Show the add location dialog
     */
    private void showAddLocationDialog(){
        // set up the dialog
        LovelyCustomDialog dialog =  new LovelyCustomDialog(getContext())
                .setView(R.layout.dialog_add_location)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.add_location)
                .setIcon(R.drawable.google_maps_light);
        // add listeners
        dialog.setListener(R.id.btnDialogAddLocation, btn -> {
            // get reference to the parent layout so we can find the EditText
            ConstraintLayout root = (ConstraintLayout) btn.getParent();
            EditText txtDialogLocation = root.findViewById(R.id.txtAddLocation);
            // add location to database
            addLocation(txtDialogLocation.getText().toString());
            dialog.dismiss();
            rvLocations.smoothScrollToPosition(locations.size() - 1);

        });
        // show the dialog
        dialog.show();
    }

    /**
     * Add a new location to the database and update the list of locations
     * @param locationName - the new location's name
     */
    private void addLocation(String locationName){

        // first add the location to the database
        realm.executeTransaction(r -> {
            Location location = r.createObject(Location.class, locations.size() + 1);
            location.setName(locationName);
        });

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
