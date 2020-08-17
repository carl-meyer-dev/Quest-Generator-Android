package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.ui.adapters.ActionsAdapter;
import com.carlmeyer.questgeneratordemo.ui.adapters.DBActionsAdapter;
import com.carlmeyer.questgeneratordemo.ui.viewholders.ActionViewHolder;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class StoryFragmentBuilderFragment extends Fragment implements ActionViewHolder.OnActionListener {

    private Realm realm;
    private RecyclerView rvActions;
    private DBActionsAdapter actionsAdapter;
    private List<DBAction> actions = new ArrayList<>();
    private List<String> actionsNames = new ArrayList<>();
    private List<String> motivationNames = new ArrayList<>();
    private Button btnAddAction;
    private EditText txtMotivation;
    private EditText txtDescription;
    private EditText txtStoryFragmentDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_story_builder, container, false);

        realm = Realm.getDefaultInstance();

        rvActions = root.findViewById(R.id.rvStoryBuilderActions);

        btnAddAction = root.findViewById(R.id.btnAddAction);

        txtMotivation = root.findViewById(R.id.txtStoryFragmentMotive);

        txtDescription = root.findViewById(R.id.txtStoryFragmentDescription);

        txtStoryFragmentDialog = root.findViewById(R.id.txtStoryFragmentDialog);

        setActionNames();

        setMotivationNames();

        setUpUI();

        setUpRecyclerView();

        return root;
    }



    /**
     * Set up UI onclick listeners etc
     */
    private void setUpUI() {
        btnAddAction.setOnClickListener(v -> showAddActionDialog());
        txtMotivation.setOnFocusChangeListener((v1, hasFocus) -> {
            // when location edit text is clicked and gains focus display a choice dialog of locations
            if (hasFocus) {
                new LovelyChoiceDialog(getContext())
                        .setTopColorRes(R.color.colorPrimary)
                        .setTitle(R.string.motivation)
                        .setIcon(R.drawable.comment_account_light)
                        .setMessage(R.string.choose_a_motivation)
                        .setItems(motivationNames, (position, motivation) -> {
                            // when a location is selected, set the location txt of the enemy and dismiss
                            txtMotivation.setText(motivation);
                            // clear focus so that you can click on it again once dialog closes
                            txtMotivation.clearFocus();
                        })
                        .show();
            }

        });
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvActions.setLayoutManager(layoutManager);
        // Initialize and set actionsAdapter with list of actions
        actionsAdapter = new DBActionsAdapter(actions, this);
        rvActions.setAdapter(actionsAdapter);
    }

    @Override
    public void onActionClick(int position) {

        DBAction selectedAction = actionsAdapter.getItem(position);
        Log.d("POSITION", position + "");

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.sword_cross_light)
                .setTitle("Remove Action?")
                .setPositiveButton(R.string.yes, v2 -> {
                    removeAction(selectedAction);
                })
                .setNegativeButton(R.string.no, v2 -> {
                })
                .show();
    }

    private void removeAction(DBAction selectedAction) {
        actions.remove(selectedAction);
        actionsAdapter.notifyDataSetChanged();
    }

    /**
     * Assign a new actions to enemies and npcs that had the action that is going to be deleted
     */
    private void showAddActionDialog() {

        new LovelyChoiceDialog(getContext())
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.actions)
                .setIcon(R.drawable.google_maps_light)
                .setMessage(R.string.choose_an_action)
                .setItems(actionsNames, (position, action) -> {
                    DBAction newAction = realm.where(DBAction.class).equalTo("action", action).findFirst();
                    actions.add(newAction);
                    actionsAdapter.notifyDataSetChanged();
                })
                .show();
    }

    /**
     * Get a string list of action names to use in actions choice dialog
     */
    private void setActionNames() {

        actionsNames = new ArrayList<>();

        RealmResults<DBAction> actionsFromDB = realm.where(DBAction.class).findAll();

        for (DBAction action : actionsFromDB) {
            actionsNames.add(action.getAction());
        }

    }

    private void setMotivationNames() {

        motivationNames = new ArrayList<>();

        RealmResults<Motivation> motivesFromDB = realm.where(Motivation.class).findAll();

        for (Motivation motivation : motivesFromDB) {
            Log.d("Motivations From DB", motivation.getMotivation());
            motivationNames.add(motivation.getMotivation());
        }
    }
}