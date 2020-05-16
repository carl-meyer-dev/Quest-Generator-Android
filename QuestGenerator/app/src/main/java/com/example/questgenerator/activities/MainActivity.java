package com.example.questgenerator.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.questgenerator.R;
import com.example.questgenerator.generator.QuestGenerator;
import com.example.questgenerator.generator.QuestReader;
import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Quest;
import com.example.questgenerator.utils.Data;
import com.example.questgenerator.utils.Motives;
import com.example.questgenerator.utils.StoryFragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {


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

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Realm DB
        InitializeDB(this);
        // Get the default instance of the realm DB. (We don't wont to create a new instance each time)
        realm = Realm.getDefaultInstance();


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

    /**
     * Initializes Realm DB with default configurations
     * @param context - application context
     */
    private void InitializeDB(Context context){
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder().name("questgenerator.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Make sure we close our Realm instance so we don't have memory leaks
    }
}
