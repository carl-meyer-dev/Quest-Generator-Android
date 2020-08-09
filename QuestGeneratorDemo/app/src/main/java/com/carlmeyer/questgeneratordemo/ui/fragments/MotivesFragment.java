package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;
import com.carlmeyer.questgeneratordemo.ui.adapters.MotivesAdapter;
import com.carlmeyer.questgeneratordemo.ui.viewholders.MotivationViewHolder;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;

public class MotivesFragment extends Fragment implements MotivationViewHolder.OnMotivationListener {

    private Realm realm;
    private RecyclerView rvMotivations;
    private Button btnAddMotivation;
    private OrderedRealmCollection<Motivation> motives;
    MotivesAdapter motivesAdapter;
    private List<String> motivationsNames;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motives, container, false);

        realm = Realm.getDefaultInstance();
        btnAddMotivation = root.findViewById(R.id.btnAddMotivation);
        rvMotivations = root.findViewById(R.id.rvMotives);

        getMotivations();

        setUpUI();

        setUpRecyclerView();
        return root;
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMotivations.setLayoutManager(layoutManager);
        // Initialize and set motivesAdapter with list of Motivations
        motivesAdapter = new MotivesAdapter(motives, this::onMotivationClick);
        rvMotivations.setAdapter(motivesAdapter);
    }

    /**
     * Set up UI onclick listeners etc
     */
    private void setUpUI() {
        btnAddMotivation.setOnClickListener(v -> {
            showAddMotivationDialog();
        });
    }

    /**
     * Setup, Configure and Show the add motivation dialog
     */
    private void showAddMotivationDialog() {
        // set up the dialog
        LovelyCustomDialog dialog = new LovelyCustomDialog(getContext())
                .setView(R.layout.dialog_add_motivation)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.add_motivation)
                .setIcon(R.drawable.sword_cross_light);

        dialog.configureView(v -> {
            EditText txtDialogMotivation = v.findViewById(R.id.txtAddMotivation);
            Button btnDialogAddMotivation = v.findViewById(R.id.btnDialogAddMotivation);

            btnDialogAddMotivation.setOnClickListener(v1 -> {
                if (txtDialogMotivation.getText().toString().isEmpty()) {
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.motivation_may_not_be_empty)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                } else {
                    addMotivation(txtDialogMotivation.getText().toString());
                    dialog.dismiss();
                }

            });

        });

        // show the dialog
        dialog.show();
    }

    /**
     * Add a new motivation to the database and update the list of Motivations
     *
     * @param motivationName - the new motivation's name
     */
    private void addMotivation(String motivationName) {

        // first add the Motivation to the database
        realm.executeTransaction(r -> {
            // unfortunately since realm does not support auto increment ID's yet we need to
            // get the next id before adding the new motivation
            long nextID = (long) (r.where(Motivation.class).max("id")) + 1;
            Motivation motivation = r.createObject(Motivation.class, nextID);
            motivation.setMotivation(motivationName);
        });

        scrollToMotivation(motivationName);
    }

    /**
     * Get the motives from the DB and sort them alphabetically
     */
    private void getMotivations() {
        motives = realm.where(Motivation.class).findAll();
        motives = motives.sort("motivation");
    }

    /*
     * It is good practice to null the reference from the view to the adapter when it is no longer needed.
     * Because the <code>RealmRecyclerViewAdapter</code> registers itself as a <code>RealmResult.ChangeListener</code>
     * the view may still be reachable if anybody is still holding a reference to the <code>RealmResult>.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        rvMotivations.setAdapter(null);
        realm.close();
    }

    @Override
    public void onMotivationClick(int position) {
        // get reference to selected motivation
        Motivation selectedMotivation = motivesAdapter.getItem(position);
        if (selectedMotivation != null) {
            // get live motivation object from Realm
            Motivation motivation = realm.where(Motivation.class).equalTo("motivation", selectedMotivation.getMotivation()).findFirst();
            showEditMotivationDialog(motivation);
        } else {
            Log.e("ERROR", "Could not find Motivation");
            // Show error dialog if motivation could not be found
            new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorAccent)
                    .setIcon(R.drawable.alert_box_light)
                    .setTitle(R.string.error)
                    .setMessage(R.string.could_not_find_motivation)
                    .setPositiveButton(android.R.string.ok, v2 -> {
                    })
                    .show();
        }
    }

    private void showEditMotivationDialog(Motivation motivation) {
        // set up the dialog
        LovelyCustomDialog dialog = new LovelyCustomDialog(getContext())
                .setView(R.layout.dialog_edit_motivation)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.edit_motivation)
                .setIcon(R.drawable.google_maps_light);

        dialog.configureView(v -> {
            EditText txtDialogMotivation = v.findViewById(R.id.txtEditMotivation);
            txtDialogMotivation.setText(motivation.getMotivation());
            Button btnDialogDeleteMotivation = v.findViewById(R.id.btnDialogDelete);
            Button btnDialogSaveMotivation = v.findViewById(R.id.btnDialogSave);
            btnDialogDeleteMotivation.setOnClickListener(v1 -> {
                // add motivation to database
                deleteMotivation(motivation);
                dialog.dismiss();
            });

            btnDialogSaveMotivation.setOnClickListener(v1 -> {
                if (txtDialogMotivation.getText().toString().isEmpty()) {
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.motivation_may_not_be_empty)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                } else {
                    updateMotivation(motivation, txtDialogMotivation.getText().toString());
                    dialog.dismiss();
                }
            });

        });

        dialog.show();
    }

    /**
     * Update an existing motivation
     *
     * @param motivation            - existing motivation
     * @param updatedMotivationName - new motivation name
     */
    private void updateMotivation(Motivation motivation, String updatedMotivationName) {

        realm.executeTransaction(r -> {
            Motivation l = r.where(Motivation.class).equalTo("motivation", motivation.getMotivation()).findFirst();
            if (l != null) {
                l.setMotivation(updatedMotivationName);
                r.insertOrUpdate(l);
            }
        });

        scrollToMotivation(updatedMotivationName);

    }


    /**
     * Delete a motivation
     *
     * @param motivation - motivation to delete
     */
    private void deleteMotivation(Motivation motivation) {
        try {
            realm.executeTransaction(r -> {
                Motivation motivationToDelete = r.where(Motivation.class).equalTo("id", motivation.getId()).findFirst();
                if (motivationToDelete != null) {
                    /* need to ask user to what the new motivation must be for
                    NPCs and Enemies that had the motivation that is going to be deleted */
                    if(motives.size() > 1){
                        showSelectNewMotivationDialog(motivationToDelete);
                    }else{
                        /* Must have at least one motivation, so need to show information dialog to
                        inform the user that they need to add another motivation in order to delete
                        this one */
                        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.colorPrimary)
                                .setButtonsColorRes(R.color.colorAccent)
                                .setIcon(R.drawable.alert_box_light)
                                .setTitle(R.string.attention)
                                .setMessage(R.string.can_not_delete_last_motivation)
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
                            .setMessage(R.string.could_not_find_motivation)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", "Could not delete Motivation");
            // Show error dialog
            new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorAccent)
                    .setIcon(R.drawable.alert_box_light)
                    .setTitle(R.string.error)
                    .setMessage(R.string.could_not_delete_motivation)
                    .setPositiveButton(android.R.string.ok, v2 -> {
                    })
                    .show();

        }
    }

    /**
     * Assign a new motivations to enemies and npcs that had the motivation that is going to be deleted
     */
    private void showSelectNewMotivationDialog(Motivation motivationToDelete){

        List<String> filteredMotivationNames = new ArrayList<>();

        setMotivationNames();

        for (String motivationName : motivationsNames){
            if(!motivationName.equals(motivationToDelete.getMotivation())){
                filteredMotivationNames.add(motivationName);
            }
        }

        new LovelyChoiceDialog(getContext())
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.motivations)
                .setIcon(R.drawable.google_maps_light)
                .setMessage(R.string.choose_a_new_motivation)
                .setItems(filteredMotivationNames, (position, motivation) -> {
                    Motivation newMotivation = realm.where(Motivation.class).equalTo("motivation", motivation).findFirst();
                    Log.d("New Motivation", motivation);
                    Log.d("New Motivation from DB", newMotivation.getMotivation());
                    updateExistingStoryFragmentMotivations(motivationToDelete, newMotivation);

                })
                .show();
    }

    /**
     * Get a string list of motivation names to use in motivations choice dialog
     */
    private void setMotivationNames() {

        motivationsNames = new ArrayList<>();

        for (Motivation motivation : motives) {
            motivationsNames.add(motivation.getMotivation());
        }

    }

    /**
     * Update motivation for existing story fragments to a new motivation
     * @param oldMotivation - old motivation of npcs and enemies
     * @param newMotivation - new motivation of npcs and enemies
     */
    private void updateExistingStoryFragmentMotivations(Motivation oldMotivation, Motivation newMotivation) {
        realm.executeTransaction(r -> {
            RealmList<StoryFragment> storyFragments = oldMotivation.getStoryFragments();

            for (StoryFragment sf : storyFragments) {
                sf.setMotivation(newMotivation.getMotivation());
            }

            newMotivation.setStoryFragments(storyFragments);

            oldMotivation.deleteFromRealm();

            getMotivations();

        });
    }

    private void scrollToMotivation(String motivationName) {
        // get the position of the motivation that has been inserted alphabetically into the list
        int position = 0;
        for (Motivation motivation : motives) {
            if (motivation.getMotivation().equals(motivationName)) {
                break;
            }
            position++;
        }
        // scroll to that position
        rvMotivations.smoothScrollToPosition(position);
    }

}
