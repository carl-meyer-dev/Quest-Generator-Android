package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Gather;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestGenerator;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestReader;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Quest;
import com.carlmeyer.questgeneratordemo.ui.adapters.ActionsAdapter;
import com.carlmeyer.questgeneratordemo.ui.viewholders.ActionViewHolder;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class QuestGeneratorFragment extends Fragment implements ActionViewHolder.OnActionListener {

    private String[] motivations = new String[]{Motives.KNOWLEDGE, Motives.COMFORT, Motives.JUSTICE};

    private TextView tvQuestText;

    private RecyclerView rvActions;
    private ActionsAdapter actionsAdapter;
    private List<Action> questSteps;
    private Quest quest;

    private Stack<List<Action>> questStack = new Stack<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quest_generator, container, false);

        tvQuestText = root.findViewById(R.id.tvQuestText);

        Button btnGenerateQuest = root.findViewById(R.id.btnQuestGenerator);

        rvActions = root.findViewById(R.id.rvActions);

        btnGenerateQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQuest();
            }
        });

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void generateQuest() {
        Random random = new Random();
        QuestGenerator questGenerator = QuestGenerator.getInstance();

        quest = null;

        // get a random quest motivation
        String questMotivation = motivations[random.nextInt(motivations.length - 1)];

        int minimumComplexity = 8;
        switch (questMotivation) {
            case Motives.KNOWLEDGE:
                // Generate Knowledge Quest
                quest = questGenerator.getQuest(Motives.KNOWLEDGE, minimumComplexity);
                break;
            case Motives.COMFORT:
                // Generate comfort quest
                quest = questGenerator.getQuest(Motives.COMFORT, minimumComplexity);
                break;
            case Motives.JUSTICE:
                // Generate Justice Quest
                quest = questGenerator.getQuest(Motives.JUSTICE, minimumComplexity);
                break;
        }

        QuestReader questReader = new QuestReader();
        // Read the quest using the Quest Reader
        if (quest != null) {
            questReader.readQuest(quest);
            String questDescriptionText = questReader.getQuestDescriptionText();
            String questMotivationText = questReader.getQuestMotivationText();
            String questStepsText = questReader.getQuestStepsToString();
            questSteps = quest.root.subActions;

            setUpRecyclerView();

            tvQuestText.setText(quest.questText);
            showQuestDialog(quest);

        }
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvActions.setLayoutManager(layoutManager);
        // Initialize and set locationsAdapter with list of locations
        actionsAdapter = new ActionsAdapter(questSteps, this);
        rvActions.setAdapter(actionsAdapter);
    }

    private void showQuestDialog(Quest quest) {
        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.script_text_light)
                .setTitle(R.string.quest)
                .setMessage(quest.dialog)
                .setPositiveButton(R.string.accept, v2 -> {
                })
                .show();
    }

    @Override
    public void onActionClick(int position) {

        Action selectedAction = actionsAdapter.getItem(position);
        Log.d("POSITION", position + "");

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.human_greeting_light)
                .setTitle(selectedAction.actionText + "?")
                .setMessage(selectedAction.actionDialog)
                .setPositiveButton(R.string.yes, v2 -> {
                    if (position == 0) {
                        // can perform action
                        performAction(selectedAction);
                    } else {
                        // needs to perform previous action
                        performPreviousActionFirst(position - 1, selectedAction);
                    }
                })
                .setNegativeButton(R.string.no, v2 -> {
                })
                .show();
    }

    /**
     * Perform the action that was selected.
     * If no subactions simple complete the action and remove it from the list
     * else if it has sub actions start subQuest
     * @param action - selected action to perform
     */
    private void performAction(Action action) {
        if (action.subActions.isEmpty()) {
            completeAction(action);
        } else {
            startSubQuest(action);
        }
    }

    private void completeAction(Action action) {
        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.check_bold)
                .setMessage("You " + action.actionText)
                .setPositiveButton(R.string.ok, v2 -> {
                    questSteps.remove(0);
                    actionsAdapter.notifyItemRemoved(0);
                })
                .show();
    }

    /**
     * Start the subquest that needs to be completed to perform root action
     * @param root
     */
    private void startSubQuest(Action root){
        StringBuilder subQuestText = new StringBuilder();
        subQuestText
                .append("Before you can ")
                .append(root.actionText)
                .append(" you need to do the following: ").append("\n");

        int step = 1;
        for (Action action : root.subActions){
            subQuestText.append(step).append(". ").append(action.actionText).append("\n").append("\n");
            step++;
        }

        questStack.push(questSteps);
        questSteps = root.subActions;
        actionsAdapter.notifyDataSetChanged();

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.alert_box_light)
                .setMessage(subQuestText.toString())
                .setPositiveButton(R.string.ok, v2 -> {

                })
                .show();


     }

    private void performPreviousActionFirst(int parentPosition, Action action) {
        Action parentAction = actionsAdapter.getItem(parentPosition);
        Log.d("POSITION", parentPosition + "");

        StringBuilder performPreviousText = new StringBuilder();

        performPreviousText
                .append("Before you can ")
                .append(action.actionText)
                .append(" you first need to ")
                .append(parentAction.actionText);

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.alert_box_light)
                .setMessage(performPreviousText.toString())
                .setPositiveButton(R.string.ok, v2 -> {

                })
                .show();
    }
}
