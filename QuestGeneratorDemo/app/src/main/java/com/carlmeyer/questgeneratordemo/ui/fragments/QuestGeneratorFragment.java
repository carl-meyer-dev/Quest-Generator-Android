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
import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestGenerator;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestReader;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Quest;
import com.carlmeyer.questgeneratordemo.ui.adapters.ActionsAdapter;
import com.carlmeyer.questgeneratordemo.ui.adapters.LocationsAdapter;
import com.carlmeyer.questgeneratordemo.ui.viewholders.ActionViewHolder;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;
import java.util.Random;

import io.realm.OrderedRealmCollection;

public class  QuestGeneratorFragment extends Fragment implements ActionViewHolder.OnActionListener {

    private String[] motivations = new String[]{Motives.KNOWLEDGE, Motives.COMFORT, Motives.JUSTICE};

    private TextView tvMotivation;
    private TextView tvDescription;
    private TextView tvQuest;

    private RecyclerView rvActions;
    List<Action> questSteps;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quest_generator, container, false);

        tvMotivation = root.findViewById(R.id.tvMotivation);
        tvDescription = root.findViewById(R.id.tvDescription);
        tvQuest = root.findViewById(R.id.tvQuest);
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

        Quest quest = null;

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
            questSteps = questReader.getQuestSteps();

            setUpRecyclerView();

            tvMotivation.setText("Motivation : " + questMotivationText);
            tvDescription.setText("Description : " + questDescriptionText);
            tvQuest.setText(questStepsText);

        } else {
            tvQuest.setText("Error! Quest is null!");
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
        ActionsAdapter actionsAdapter = new ActionsAdapter(questSteps, this);
        rvActions.setAdapter(actionsAdapter);
    }

    @Override
    public void onActionClick(int position) {
        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.human_greeting_light)
                .setTitle(R.string.dialog)
                .setMessage(R.string.test_dialog)
                .setPositiveButton(R.string.yes, v2 -> {
                })
                .setNegativeButton(R.string.no, v2 -> {
                })
                .show();
    }
}
