package com.example.questgenerator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.questgenerator.R;
import com.example.questgenerator.generator.QuestGenerator;
import com.example.questgenerator.generator.QuestReader;
import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;
import com.example.questgenerator.models.Quest;
import com.example.questgenerator.models.StoryFragment;
import com.example.questgenerator.utils.Motives;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<Location> locations;
    public List<NPC> npcs;
    public List<Enemy> enemies;
    public List<Item> items;

    public List<StoryFragment> knowledgeStoryFragments;
    public List<StoryFragment> comfortStoryFragments;
    public List<StoryFragment> justiceStoryFragments;

    String[] motivations = new String[]{ Motives.KNOWLEDGE, Motives.COMFORT, Motives.JUSTICE };

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
        setContentView(R.layout.activity_main);
        random = new Random();

        tvMotivation = findViewById(R.id.tvMotivation);
        tvDescription = findViewById(R.id.tvDescription);
        tvQuest = findViewById(R.id.tvQuest);
        btnGenerateQuest = findViewById(R.id.btnGenerateQuest);

        btnGenerateQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQuest();
            }
        });

    }

    private void generateQuest() {

        questGenerator = QuestGenerator.getInstance(this);

        Quest quest = null;

        // get a random quest motivation
        String questMotivation = motivations[random.nextInt(motivations.length - 1)];

        if(questMotivation.equals(Motives.KNOWLEDGE)){
            // Generate Knowledge Quest
            quest = questGenerator.getQuest(Motives.KNOWLEDGE, minimumComplexity);
        }else if (questMotivation.equals(Motives.COMFORT)){
            // Generate comfort quest
            quest = questGenerator.getQuest(Motives.COMFORT, minimumComplexity);
        }else if(questMotivation.equals(Motives.JUSTICE)){
            // Generate Justice Quest
            quest = questGenerator.getQuest(Motives.JUSTICE, minimumComplexity);
        }

        questReader = new QuestReader();
        // Read the quest using the Quest Reader
        questReader.readQuest(quest);

        questDescriptionText = questReader.getQuestDescriptionText();
        questMotivationText = questReader.getQuestMotivationText();
        questStepsText = questReader.getQuestStepsText();
        questSteps = questReader.getQuestSteps();

        tvMotivation.setText("Motivation : " + questMotivationText);
        tvMotivation.setText("Description : " + questDescriptionText);
        tvMotivation.setText(questMotivationText);
    }
}
