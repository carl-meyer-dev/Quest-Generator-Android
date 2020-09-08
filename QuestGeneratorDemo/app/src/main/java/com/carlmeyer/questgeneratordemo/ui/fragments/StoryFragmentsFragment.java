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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
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
        btnAddStoryFragment.setOnClickListener(v -> addStoryFragment());
    }

    /**
     * Setup, Configure and Show the add storyFragment dialog
     */
    private void addStoryFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.nav_story_fragment_builder);
    }


    /**
     * Get the storyFragments from the DB and sort them alphabetically
     */
    private void getStoryFragments() {
        storyFragments = realm.where(StoryFragment.class).findAll();
        storyFragments = storyFragments.sort("id");
    }

    private void deleteStoryFragmentDialog(StoryFragment storyFragment){
        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.alert_box_light)
                .setTitle(R.string.attention)
                .setMessage(R.string.are_you_sure_you_want_delete)
                .setPositiveButton(android.R.string.yes, v2 -> {
                    deleteStoryFragment(storyFragment);
                })
                .setNegativeButton(android.R.string.no, v -> {

                })
                .show();
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


            StringBuilder info = new StringBuilder();

            info.append("Motivation: ").append(storyFragment.getMotivation()).append("\n").append("\n");
            info.append("Actions: ").append("\n");
            int index = 1;
            for (String action : storyFragment.getActions()){
                info.append(index).append(". ").append(action).append("\n");
                index++;
            }
            info.append("\n").append("Dialog: ").append(storyFragment.getQuestDialog());

            new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.colorAccent)
                    .setIcon(R.drawable.comment_account_light)
                    .setTitle(storyFragment.getDescription())
                    .setMessage(info.toString())
                    .setPositiveButton(R.string.edit, v2 -> {
                        goToStoryFragmentBuilder(selectedStoryFragment);
                    })
                    .setNegativeButton(R.string.delete, v -> {
                        deleteStoryFragmentDialog(selectedStoryFragment);
                    })
                    .setNeutralButton(R.string.cancel, v -> {

                    })
                    .show();

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
        Bundle bundle = new Bundle();
        bundle.putBoolean("edit", true);
        bundle.putSerializable("storyfragment", selectedStoryFragment);
        NavHostFragment.findNavController(this).navigate(R.id.nav_story_fragment_builder, bundle);
    }


}
