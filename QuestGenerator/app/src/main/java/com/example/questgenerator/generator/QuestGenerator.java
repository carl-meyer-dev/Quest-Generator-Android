package com.example.questgenerator.generator;

import android.os.Debug;
import android.util.Log;

import com.example.questgenerator.actions.Subquest;
import com.example.questgenerator.activities.MainActivity;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;
import com.example.questgenerator.models.Quest;

import java.util.List;

public class QuestGenerator {

    // Reference to main activity
    MainActivity activity;

    // Local copies of all data
    List<Location> locations;
    List<NPC> npcs;
    List<Enemy> enemies;
    List<Item> items;

    private static QuestGenerator instance;

    public static QuestGenerator getInstance(MainActivity activity){
        if(instance == null){
            instance = new QuestGenerator(activity);
        }
        return instance;
    }

    private QuestGenerator(MainActivity activity){
        this.activity = activity;
    }

    public Quest getQuest(String motive, int minimumComplexity){
        int failCatch = 0;
        Subquest root = getActions(motive);
        Quest quest = new Quest(root);

        while (quest.getDepth() < minimumComplexity || quest.getDepth() > 20){
            if(failCatch > 100){
                Log.d("Error", "Can't find path, breaking out.");
                break;
            }
            quest = new Quest(getActions(motive));
            failCatch++;
        }

        quest.motivation = motive;
        quest.description = root.actionText;
        // Will add quest meta data here, such as text, title, etc
        return quest;
    }

    // Get the appropriate actions for the subquest based on motive
    public Subquest getActions(String motive){
        
    }





}
