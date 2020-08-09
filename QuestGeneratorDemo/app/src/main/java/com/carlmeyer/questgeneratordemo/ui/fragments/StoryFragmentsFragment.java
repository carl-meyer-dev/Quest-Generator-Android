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
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;
import com.carlmeyer.questgeneratordemo.ui.adapters.StoryFragmentsAdapter;
import com.carlmeyer.questgeneratordemo.ui.viewholders.StoryFragmentViewHolder;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class StoryFragmentsFragment extends Fragment implements StoryFragmentViewHolder.OnStoryFragmentListener {

    private Realm realm;
    private RecyclerView rvStoryFragments;
    private Button btnAddStoryFragment;
    private OrderedRealmCollection<StoryFragment> storyFragments;
    StoryFragmentsAdapter storyFragmentsAdapter;
    private List<String> storyFragmentsNames;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_story_fragments, container, false);
        rvStoryFragments = root.findViewById(R.id.rvStoryFragments);
        realm = Realm.getDefaultInstance();
        btnAddStoryFragment = root.findViewById(R.id.btnAddStoryFragment);
        rvStoryFragments = root.findViewById(R.id.rvStoryFragments);
        getStoryFragments();
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
        rvStoryFragments.setLayoutManager(layoutManager);
        // Initialize and set storyFragmentsAdapter with list of storyFragments
        storyFragmentsAdapter = new StoryFragmentsAdapter(storyFragments, this::onStoryFragmentClick);
        rvStoryFragments.setAdapter(storyFragmentsAdapter);
    }

    /**
     * Set up UI onclick listeners etc
     */
    private void setUpUI() {
        btnAddStoryFragment.setOnClickListener(v -> showAddStoryFragmentDialog());
    }

    /**
     * Setup, Configure and Show the add storyFragment dialog
     */
    private void showAddStoryFragmentDialog() {
        // set up the dialog
        LovelyCustomDialog dialog = new LovelyCustomDialog(getContext())
                .setView(R.layout.dialog_add_story_fragment)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.add_story_fragment)
                .setIcon(R.drawable.google_maps_light);

        dialog.configureView(v -> {
            EditText txtDialogStoryFragment = v.findViewById(R.id.txtAddStoryFragment);
            Button btnDialogAddStoryFragment = v.findViewById(R.id.btnDialogAddStoryFragment);
            btnDialogAddStoryFragment.setOnClickListener(v1 -> {
                if (txtDialogStoryFragment.getText().toString().isEmpty()) {
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.story_fragment_may_not_be_empty)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                } else {
                    // add storyFragment to database
                    addStoryFragment(Integer.parseInt(txtDialogStoryFragment.getText().toString()));
                    dialog.dismiss();
                }

            });

        });

        dialog.show();
    }

    /**
     * Show Edit StoryFragment Dialog
     *
     * @param storyFragment - the storyFragment being edited
     */
    private void showEditStoryFragmentDialog(StoryFragment storyFragment) {
        // set up the dialog
        LovelyCustomDialog dialog = new LovelyCustomDialog(getContext())
                .setView(R.layout.dialog_edit_story_fragment)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.edit_story_fragment)
                .setIcon(R.drawable.google_maps_light);

        dialog.configureView(v -> {
            EditText txtDialogStoryFragment = v.findViewById(R.id.txtEditStoryFragment);
            txtDialogStoryFragment.setText(storyFragment.getDescription());
            Button btnDialogDeleteStoryFragment = v.findViewById(R.id.btnDialogDelete);
            Button btnDialogSaveStoryFragment = v.findViewById(R.id.btnDialogSave);
            btnDialogDeleteStoryFragment.setOnClickListener(v1 -> {
                // add storyFragment to database
                deleteStoryFragment(storyFragment);
                dialog.dismiss();
            });

            btnDialogSaveStoryFragment.setOnClickListener(v1 -> {
                if (txtDialogStoryFragment.getText().toString().isEmpty()) {
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.story_fragment_may_not_be_empty)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                } else {
                    updateStoryFragment(storyFragment, txtDialogStoryFragment.getText().toString());
                    dialog.dismiss();
                }
            });

        });

        dialog.show();
    }


    /**
     * Get the storyFragments from the DB and sort them alphabetically
     */
    private void getStoryFragments() {
        storyFragments = realm.where(StoryFragment.class).findAll();
        storyFragments = storyFragments.sort("id");
    }

    /**
     * Add a new storyFragment to the database and update the list of storyFragments
     *
     * @param storyFragmentId - the new storyFragment's id
     */
    private void addStoryFragment(int storyFragmentId) {

        // first add the storyFragment to the database
        realm.executeTransaction(r -> {
            // unfortunately since realm does not support auto increment ID's yet we need to
            // get the next id before adding the new storyFragment
            long nextID = (long) (r.where(StoryFragment.class).max("id")) + 1;
            StoryFragment storyFragment = r.createObject(StoryFragment.class, nextID);
            storyFragment.setId(storyFragmentId);
        });

        // get the position of the item that has been inserted alphabetically into the list
        int position = 0;
        for (StoryFragment storyFragment : storyFragments) {
            if (storyFragment.getId() == (storyFragmentId)) {
                break;
            }
            position++;
        }
        // scroll to that position
        rvStoryFragments.smoothScrollToPosition(position);


    }

    /**
     * Update an existing storyFragment
     * @param storyFragment - existing storyFragment
     * @param updatedStoryFragmentName - new storyFragment id
     */
    private void updateStoryFragment(StoryFragment storyFragment, String updatedStoryFragmentName) {

       

    }


    /**
     * Delete a storyFragment
     *
     * @param storyFragment - storyFragment to delete
     */
    private void deleteStoryFragment(StoryFragment storyFragment) {
        try {
            realm.executeTransaction(r -> {
                StoryFragment storyFragmentToDelete = r.where(StoryFragment.class).equalTo("id", storyFragment.getId()).findFirst();
                if (storyFragmentToDelete != null) {
                    /* Must have at least one storyFragment, so need to show information dialog to
                        inform the user that they need to add another storyFragment in order to delete
                        this one */
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.attention)
                            .setMessage(R.string.can_not_delete_last_story_fragment)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                } else {
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.could_not_find_story_fragment)
                            .setPositiveButton(android.R.string.ok, v2 -> {
                            })
                            .show();
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", "Could not delete StoryFragment");
            // Show error dialog
            new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorAccent)
                    .setIcon(R.drawable.alert_box_light)
                    .setTitle(R.string.error)
                    .setMessage(R.string.could_not_delete_story_fragment)
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
        rvStoryFragments.setAdapter(null);
        realm.close();
    }

    @Override
    public void onStoryFragmentClick(int position) {
        // get reference to selected storyFragment
        StoryFragment selectedStoryFragment = storyFragmentsAdapter.getItem(position);
        if (selectedStoryFragment != null) {
            // get live storyFragment object from Realm
            StoryFragment storyFragment = realm.where(StoryFragment.class).equalTo("id", selectedStoryFragment.getId()).findFirst();
            goToStoryFragmentBuilder(selectedStoryFragment);
        } else {
            Log.e("ERROR", "Could not find StoryFragment");
            // Show error dialog if storyFragment could not be found
            new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorAccent)
                    .setIcon(R.drawable.alert_box_light)
                    .setTitle(R.string.error)
                    .setMessage(R.string.could_not_find_story_fragment)
                    .setPositiveButton(android.R.string.ok, v2 -> {
                    })
                    .show();
        }
    }

    private void goToStoryFragmentBuilder(StoryFragment selectedStoryFragment) {

    }


}
