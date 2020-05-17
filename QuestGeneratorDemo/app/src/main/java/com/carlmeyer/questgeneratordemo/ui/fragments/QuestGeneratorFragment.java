package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestGenerator;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestReader;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Quest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestGeneratorFragment extends Fragment {

    private String[] motivations = new String[]{Motives.KNOWLEDGE, Motives.COMFORT, Motives.JUSTICE};

    private TextView tvMotivation;
    private TextView tvDescription;
    private TextView tvQuest;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quest_generator, container, false);

        tvMotivation = root.findViewById(R.id.tvMotivation);
        tvDescription = root.findViewById(R.id.tvDescription);
        tvQuest = root.findViewById(R.id.tvQuest);
        Button btnGenerateQuest = root.findViewById(R.id.btnQuestGenerator);

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
            List<Action> questSteps = questReader.getQuestSteps();

            tvMotivation.setText("Motivation : " + questMotivationText);
            tvDescription.setText("Description : " + questDescriptionText);
            tvQuest.setText(questStepsText);

        } else {
            tvQuest.setText("Error! Quest is null!");
        }
    }
}
