package com.example.questgenerator.actions;

import com.example.questgenerator.activities.MainActivity;
import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;
import com.example.questgenerator.utils.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Goto extends Action {

    MainActivity activity;

    /**
     * Go to npc location
     * @param npc - npc you need to go to
     * @param activity - reference to main activity
     */
    public Goto(NPC npc, MainActivity activity){
        this.activity = activity;
        this.actionText = "Go to " + npc.location.name;
        this.subActions = new ArrayList<>();
        initialize(npc);
    }

    /**
     * Go to enemy location
     * @param enemy - enemy to go to
     * @param activity - reference to main activity
     */
    public Goto(Enemy enemy, MainActivity activity){
        this.activity = activity;
        this.actionText = "Go to " + enemy.location.name;
        this.subActions = new ArrayList<>();
        initialize(enemy);
    }

    /**
     * Go to location
     * @param location - location to go to
     * @param activity - reference to main activity
     */
    public Goto(Location location, MainActivity activity){
        this.activity = activity;
        this.actionText = "Goto " + location.name;
        this.subActions = new ArrayList<>();
        // No initialize for location since you simply need to goto the location
    }

    /**
     * Add any possible sub actions before going to npc
     * @param npc - npc to go to
     */
    public void initialize(NPC npc){
        // Add all the different ways the quest can go with an NPC
        List<String[]> questPatterns = new ArrayList<>();
        // Learn about npc first before Goto npc
        questPatterns.add(new String[]{Actions.LEARN});
        // Just Goto npc
        questPatterns.add(new String[]{});

        Random random = new Random();
        // Choose a random questPattern and add subActions
        for (String action : questPatterns.get(random.nextInt(questPatterns.size()))){
            if(action.equals(Actions.LEARN)){
                subActions.add(new Learn(npc, activity));
            }
        }


    }

    /**
     * Add any sub quests before going to enemy
     * @param enemy
     */
    public void initialize(Enemy enemy){
        // Add all the different ways the quest can go with an Enemy
        List<String[]> questPatterns = new ArrayList<>();
        // Learn about enemy first before Goto enemy
        questPatterns.add(new String[]{Actions.LEARN});
        // just Goto enemy
        questPatterns.add(new String[]{});

        Random random = new Random();
        // Choose a random questPattern and add subActions
        for (String action : questPatterns.get(random.nextInt(questPatterns.size()))){
            if(action.equals(Actions.LEARN)){
                subActions.add(new Learn(enemy, activity));
            }
        }
    }

}
