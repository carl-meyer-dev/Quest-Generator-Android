package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.IntRange;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;
import com.carlmeyer.questgeneratordemo.ui.adapters.DBActionsAdapter;
import com.carlmeyer.questgeneratordemo.ui.adapters.TemplateHelperAdapter;
import com.carlmeyer.questgeneratordemo.ui.interfaces.StartDragListener;
import com.carlmeyer.questgeneratordemo.ui.interfaces.StopDragListener;
import com.carlmeyer.questgeneratordemo.ui.utils.AutoFitGridLayoutManager;
import com.carlmeyer.questgeneratordemo.ui.utils.ItemMoveCallback;
import com.carlmeyer.questgeneratordemo.ui.viewholders.ActionViewHolder;
import com.carlmeyer.questgeneratordemo.ui.viewholders.TemplateHelperViewHolder;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class StoryFragmentBuilderFragment extends Fragment implements ActionViewHolder.OnActionListener, StartDragListener, StopDragListener, TemplateHelperViewHolder.OnTemplateHelperListener {

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
    private ItemTouchHelper touchHelper;
    private Button btnSaveStoryFragment;
    private Boolean edit = false;
    private RecyclerView rvTemplateHelper;
    private TemplateHelperAdapter templateHelperAdapter;
    private List<String> templateHelpers = new ArrayList<>();
    private View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_story_builder, container, false);

        realm = Realm.getDefaultInstance();

        rvActions = root.findViewById(R.id.rvStoryBuilderActions);

        rvTemplateHelper = root.findViewById(R.id.rvTemplateHelper);

        btnAddAction = root.findViewById(R.id.btnAddAction);

        btnSaveStoryFragment = root.findViewById(R.id.btnSaveStoryFragment);

        txtMotivation = root.findViewById(R.id.txtStoryFragmentMotive);

        txtDescription = root.findViewById(R.id.txtStoryFragmentDescription);

        txtStoryFragmentDialog = root.findViewById(R.id.txtStoryFragmentDialog);

        setActionNames();

        setMotivationNames();

        setUpUI();

        setUpActionsRecyclerView();

        setupTemplateHelperRecyclerView();

        return root;
    }


    /**
     * Set up UI onclick listeners etc
     */
    private void setUpUI() {
        btnAddAction.setOnClickListener(v -> showAddActionDialog());
        btnSaveStoryFragment.setOnClickListener(v -> saveStoryFragment());
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

    private void saveStoryFragment() {

        if (edit) {
            // update existing story fragment
        } else {
            realm.executeTransaction(r -> {
                long nextID = (long) (r.where(StoryFragment.class).max("id")) + 1;
                StoryFragment sf = r.createObject(StoryFragment.class, nextID);
                sf.setMotivation(txtMotivation.getText().toString());
                sf.setDescription(txtDescription.getText().toString().toLowerCase());
                sf.setActions(actions);
                sf.setQuestDialog(txtStoryFragmentDialog.getText().toString());
            });

            Navigation.findNavController(root).popBackStack();


        }
    }

    /**
     * Set up RecyclerView
     */
    private void setUpActionsRecyclerView() {
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvActions.setLayoutManager(layoutManager);
        // Initialize and set actionsAdapter with list of actions
        actionsAdapter = new DBActionsAdapter(actions, this::onActionClick, this::requestDrag, this::stopDrag);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(actionsAdapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvActions);
        rvActions.setAdapter(actionsAdapter);
    }

    private void setupTemplateHelperRecyclerView() {

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(requireContext())
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();

        rvTemplateHelper.setLayoutManager(chipsLayoutManager);
        templateHelperAdapter = new TemplateHelperAdapter(templateHelpers, this::onTemplateHelperClick);
        rvTemplateHelper.setAdapter(templateHelperAdapter);
        templateHelperAdapter.notifyDataSetChanged();

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
        notifyDataSetChanged();
    }

    private void showConfigurations(DBAction selectedAction) {

        List<String> configs = selectedAction.getConfigs();

        new LovelyChoiceDialog(getContext())
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.actions)
                .setIcon(R.drawable.google_maps_light)
                .setMessage(R.string.choose_an_action)
                .setItems(configs, (position, config) -> {

                    DBAction action = new DBAction(
                            selectedAction.getId(),
                            selectedAction.getAction(),
                            selectedAction.getConfigs(),
                            config
                    );
                    actions.add(action);
                    notifyDataSetChanged();
                })
                .show();
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
                    showConfigurations(newAction);
                })
                .show();
    }

    private void updateDialogQuestTemplateHelper() {

        templateHelpers.clear();
        HashMap<String, Integer> configCounters = new HashMap<>();

        for (DBAction action : actions) {
            int count = 0;
            String config = action.getConfig();

            if (configCounters.containsKey(config)) {
                count = configCounters.get(config);
            }
            count++;
            configCounters.put(action.getConfig(), count);

            templateHelpers.add("$" + action.getConfig() + count);
        }
        templateHelperAdapter.notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        updateDialogQuestTemplateHelper();
        actionsAdapter.notifyDataSetChanged();
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


    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }

    @Override
    public void onTemplateHelperClick(int position) {

        String selectedTemplateHelper = templateHelperAdapter.getItem(position);

        txtStoryFragmentDialog.append(" " + selectedTemplateHelper + " ");
    }

    @Override
    public void stopDrag(RecyclerView.ViewHolder viewHolder) {
        notifyDataSetChanged();
    }
}