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
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;
import com.example.questgenerator.models.Quest;
import com.example.questgenerator.models.StoryFragment;
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

        // if the DB is empty then load the default set of data
        if(realm.isEmpty()){
            loadData();
        }

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
        // Clear the Realm if the example was previously run.
        if (Realm.getDefaultConfiguration() != null) {
            Realm.deleteRealm(Realm.getDefaultConfiguration());
        }
    }

    /**
     * Load the initial set of Data for pixel dungeon
     * This will contain the default set of enemies, npcs, locations and items that is present
     * within pixel dungeon
     */
    private void loadData(){
        /* I have already set up all the default enemies, npcs, item and locations in the Data Class
           so I am going to use that class to add them to the realm db. I could have added the
           data to the db using JSON and GSON but since I already had the Data class that I made
           I decided to save some time and use that instead
         */
        Data data = new Data();
        // Start a transaction to add all the information to the realm db.
        // TODO: make async so that we do not cause the UI thread to wait for this transaction to finish
        //  note if we do above we need to deactivate generate button until the data has been added to the DB
        realm.executeTransaction(r -> {

            // TODO: Maybe thing about error handling. Maybe add try and catch when adding each data
            //  item so that the whole transaction doesn't fail and exit if by mistake there are
            //  conflicting primary keys. (e.g. if adding "rat" and add another "rat" and exception
            //  will be thrown since 2 primary keys with the same name may not exist.

            // Add all the Locations from Data to the DB
            for (int i = 0; i < data.getLocations().size(); i++){
                Location l = data.getLocations().get(i);
                Location location = r.createObject(Location.class, i + 1);
                location.setName(l.getName());
                Log.d("LoadData", "Added Location to DB: " + l.getName());
            }

            // Add all the Items from Data to the DB
            for (int j = 0; j < data.getItems().size(); j++){
                Item i = data.getItems().get(j);
                Item item = r.createObject(Item.class);
                item.setName(i.getName());
                Log.d("LoadData", "Added Item to DB: " + i.getName());
            }
            // Add all the Enemies from Data to the DB
            for (int k = 0; k < data.getEnemies().size(); k++){
                Enemy e = data.getEnemies().get(k);
                Enemy enemy = r.createObject(Enemy.class);
                enemy.setName(e.getName());
                enemy.setLocation(e.getLocation());
                Log.d("LoadData", "Added Enemy to DB: " + e.getName());

            }
            // Add all the NPCs from Data to the DB
            for (int l = 0; l < data.getNpcs().size(); l++){
                NPC n = data.getNpcs().get(l);
                NPC npc = r.createObject(NPC.class);
                npc.setName(n.getName());
                npc.setLocation(n.getLocation());
                Log.d("LoadData", "Added NPC to DB: " + n.getName());
            }

            /*
            I also want to add the initial set of Story Fragments to the DB so that we can later
            add the functionality to add and edit Story Fragments in the App.
             */

            // Get a reference to the Default Story Fragments that I have set up for the Quest Generator
            // TODO: subaction of type string[] not allowed by Realm. Need to change it
//            StoryFragments storyFragments = new StoryFragments();
//            for (StoryFragment sf : storyFragments.getAllStoryFragments()){
//                StoryFragment storyFragment = r.createObject(StoryFragment.class);
//                storyFragment.setMotive(sf.getMotive());
//                storyFragment.setDescription(sf.getDescription());
//                storyFragment.setActions(sf.getActions());
//                Log.d("LoadData", "Added Story Fragment to DB: " + sf.getMotive() + " : " + sf.getDescription());
//            }

        });





    }

    /**
     * When the activity is closed, this function will be called as per Activity Lifecycle
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Make sure we close our Realm instance so we don't have memory leaks
    }

}
