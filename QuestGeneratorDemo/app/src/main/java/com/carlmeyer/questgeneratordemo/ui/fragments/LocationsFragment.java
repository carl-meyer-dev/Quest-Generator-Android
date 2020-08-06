package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;
import com.carlmeyer.questgeneratordemo.ui.adapters.LocationsAdapter;
import com.carlmeyer.questgeneratordemo.ui.viewholders.LocationViewHolder;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class LocationsFragment extends Fragment implements LocationViewHolder.OnLocationListener {

    private Realm realm;
    private RecyclerView rvLocations;
    private Button btnAddLocation;
    private OrderedRealmCollection<Location> locations;
    LocationsAdapter locationsAdapter;
    private List<String> locationsNames;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_locations, container, false);
        rvLocations = root.findViewById(R.id.rvLocations);
        realm = Realm.getDefaultInstance();
        btnAddLocation = root.findViewById(R.id.btnAddLocation);
        rvLocations = root.findViewById(R.id.rvLocations);
        getLocations();
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
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLocations.setLayoutManager(layoutManager);
        // Initialize and set locationsAdapter with list of locations
        locationsAdapter = new LocationsAdapter(locations, this::onLocationClick);
        rvLocations.setAdapter(locationsAdapter);
    }

    /**
     * Set up UI onclick listeners etc
     */
    private void setUpUI() {
        btnAddLocation.setOnClickListener(v -> showAddLocationDialog());
    }

    /**
     * Setup, Configure and Show the add location dialog
     */
    private void showAddLocationDialog() {
        // set up the dialog
        LovelyCustomDialog dialog = new LovelyCustomDialog(getContext())
                .setView(R.layout.dialog_add_location)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.add_location)
                .setIcon(R.drawable.google_maps_light);

        dialog.configureView(v -> {
            EditText txtDialogLocation = v.findViewById(R.id.txtAddLocation);
            Button btnDialogAddLocation = v.findViewById(R.id.btnDialogAddLocation);
            btnDialogAddLocation.setOnClickListener(v1 -> {
                if (txtDialogLocation.getText().toString().isEmpty()) {
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.location_may_not_be_empty)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                } else {
                    // add location to database
                    addLocation(txtDialogLocation.getText().toString());
                    dialog.dismiss();
                }

            });

        });

        dialog.show();
    }

    /**
     * Show Edit Location Dialog
     *
     * @param location - the location being edited
     */
    private void showEditLocationDialog(Location location) {
        // set up the dialog
        LovelyCustomDialog dialog = new LovelyCustomDialog(getContext())
                .setView(R.layout.dialog_edit_location)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.edit_location)
                .setIcon(R.drawable.google_maps_light);

        dialog.configureView(v -> {
            EditText txtDialogLocation = v.findViewById(R.id.txtEditLocation);
            txtDialogLocation.setText(location.getName());
            Button btnDialogDeleteLocation = v.findViewById(R.id.btnDialogDelete);
            Button btnDialogSaveLocation = v.findViewById(R.id.btnDialogSave);
            btnDialogDeleteLocation.setOnClickListener(v1 -> {
                // add location to database
                deleteLocation(location);
                dialog.dismiss();
            });

            btnDialogSaveLocation.setOnClickListener(v1 -> {
                if (txtDialogLocation.getText().toString().isEmpty()) {
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.location_may_not_be_empty)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                } else {
                    updateLocation(location, txtDialogLocation.getText().toString());
                    dialog.dismiss();
                }
            });

        });

        dialog.show();
    }


    /**
     * Get the locations from the DB and sort them alphabetically
     */
    private void getLocations() {
        locations = realm.where(Location.class).findAll();
        locations = locations.sort("name");
        // set locationNames list to use in choice dialog
        setLocationNames();
    }

    /**
     * Add a new location to the database and update the list of locations
     *
     * @param locationName - the new location's name
     */
    private void addLocation(String locationName) {

        // first add the location to the database
        realm.executeTransaction(r -> {
            // unfortunately since realm does not support auto increment ID's yet we need to
            // get the next id before adding the new location
            long nextID = (long) (r.where(Location.class).max("id")) + 1;
            Location location = r.createObject(Location.class, nextID);
            location.setName(locationName);
        });

        // get the position of the item that has been inserted alphabetically into the list
        int position = 0;
        for (Location location : locations) {
            if (location.getName().equals(locationName)) {
                break;
            }
            position++;
        }
        // scroll to that position
        rvLocations.smoothScrollToPosition(position);


    }

    /**
     * Update an existing location
     * @param location - existing location
     * @param updatedLocationName - new location name
     */
    private void updateLocation(Location location, String updatedLocationName) {

        realm.executeTransaction(r -> {
            Location l = r.where(Location.class).equalTo("name", location.getName()).findFirst();
            if (l != null) {
                l.setName(updatedLocationName);
                r.insertOrUpdate(l);
            }
        });

    }


    /**
     * Delete a location
     *
     * @param location - location to delete
     */
    private void deleteLocation(Location location) {
        try {
            realm.executeTransaction(r -> {
                Location locationToDelete = r.where(Location.class).equalTo("name", location.getName()).findFirst();
                if (locationToDelete != null) {
                    /* need to ask user to what the new location must be for
                    NPCs and Enemies that had the location that is going to be deleted */
                    if(locations.size() > 1){
                        showSelectNewLocationDialog(locationToDelete);
                    }else{
                        /* Must have at least one location, so need to show information dialog to
                        inform the user that they need to add another location in order to delete
                        this one */
                        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.colorPrimary)
                                .setButtonsColorRes(R.color.colorAccent)
                                .setIcon(R.drawable.alert_box_light)
                                .setTitle(R.string.attention)
                                .setMessage(R.string.can_not_delete_last_location)
                                .setPositiveButton(android.R.string.ok, v2 -> {
                                })
                                .show();
                    }


                } else {
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.could_not_find_location)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", "Could not delete Location");
            // Show error dialog
            new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorAccent)
                    .setIcon(R.drawable.alert_box_light)
                    .setTitle(R.string.error)
                    .setMessage(R.string.could_not_delete_location)
                    .setPositiveButton(android.R.string.ok, v2 -> {
                    })
                    .show();

        }
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

    @Override
    public void onLocationClick(int position) {
        // get reference to selected location
        Location selectedLocation = locationsAdapter.getItem(position);
        if (selectedLocation != null) {
            // get live location object from Realm
            Location location = realm.where(Location.class).equalTo("name", selectedLocation.getName()).findFirst();
            showEditLocationDialog(location);
        } else {
            Log.e("ERROR", "Could not find Location");
            // Show error dialog if location could not be found
            new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorAccent)
                    .setIcon(R.drawable.alert_box_light)
                    .setTitle(R.string.error)
                    .setMessage(R.string.could_not_find_location)
                    .setPositiveButton(android.R.string.ok, v2 -> {
                    })
                    .show();
        }
    }

    /**
     * Assign a new locations to enemies and npcs that had the location that is going to be deleted
     */
    private void showSelectNewLocationDialog(Location locationToDelete){

        List<String> filteredLocationNames = new ArrayList<>();

        setLocationNames();

        for (String locationName : locationsNames){
            if(!locationName.equals(locationToDelete.getName())){
                filteredLocationNames.add(locationName);
            }
        }

        new LovelyChoiceDialog(getContext())
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.locations)
                .setIcon(R.drawable.google_maps_light)
                .setMessage(R.string.choose_a_new_location)
                .setItems(filteredLocationNames, (position, location) -> {
                    Location newLocation = realm.where(Location.class).equalTo("name", location).findFirst();
                    updateExistingNPCAndEnemyLocations(locationToDelete, newLocation);

                })
                .show();
    }

    /**
     * Update Existing NPCs and Enemies in a particular location to a new location
     * @param oldLocation - old location of npcs and enemies
     * @param newLocation - new location of npcs and enemies
     */
    private void updateExistingNPCAndEnemyLocations(Location oldLocation, Location newLocation){
        realm.executeTransaction(r -> {
            OrderedRealmCollection<NPC> npcs = oldLocation.getNpcs();
            OrderedRealmCollection<Enemy> enemies = oldLocation.getEnemies();

            for (NPC npc : npcs){
                npc.setLocation(newLocation);
            }

            for (Enemy enemy : enemies){
                enemy.setLocation(newLocation);
            }

            oldLocation.deleteFromRealm();

            getLocations();

        });

        // after npcs and enemies have been updated with new location, we can safely delete old location that needs to be deleted

    }

    /**
     * Get a string list of location names to use in locations choice dialog
     */
    private void setLocationNames() {

        locationsNames = new ArrayList<>();

        for (Location location : locations) {
            locationsNames.add(location.getName());
        }

    }
}
