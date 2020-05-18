package com.carlmeyer.questgeneratordemo.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestGenerator;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestReader;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Quest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestGeneratorActivity extends AppCompatActivity {

    String[] motivations = new String[]{Motives.KNOWLEDGE, Motives.COMFORT, Motives.JUSTICE};

    int minimumComplexity = 8;

    QuestGenerator questGenerator;
    QuestReader questReader;

    String questDescriptionText = "";
    String questMotivationText = "";
    String questStepsText = "";
    List<Action> questSteps = new ArrayList<>();

    Random random;

    TextView tvMotivation;
    TextView tvDescription;
    TextView tvQuest;
    Button btnGenerateQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_generator);

        random = new Random();

        tvMotivation = findViewById(R.id.tvMotivation);
        tvDescription = findViewById(R.id.tvDescription);
        tvQuest = findViewById(R.id.tvQuest);
        btnGenerateQuest = findViewById(R.id.btnQuestGenerator);

        btnGenerateQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQuest();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void generateQuest() {

        questGenerator = QuestGenerator.getInstance();

        Quest quest = null;

        // get a random quest motivation
        String questMotivation = motivations[random.nextInt(motivations.length - 1)];

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

        questReader = new QuestReader();
        // Read the quest using the Quest Reader
        if (quest != null) {
            questReader.readQuest(quest);
            questDescriptionText = questReader.getQuestDescriptionText();
            questMotivationText = questReader.getQuestMotivationText();
            questStepsText = questReader.getQuestStepsToString();
            questSteps = questReader.getQuestSteps();

            tvMotivation.setText("Motivation : " + questMotivationText);
            tvDescription.setText("Description : " + questDescriptionText);
            tvQuest.setText(questStepsText);

        }else{
            tvQuest.setText("Error! Quest is null!");

        }
    }
}
