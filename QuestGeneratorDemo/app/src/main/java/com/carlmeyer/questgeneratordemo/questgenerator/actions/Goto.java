package com.carlmeyer.questgeneratordemo.questgenerator.actions;


import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Goto extends Action {


    /**
     * Go to npc location
     *
     * @param npc - npc you need to go to
     */
    public Goto(NPC npc) {
        this.actionName = Actions.GOTO;
        this.actionDescription = "Go to the " + npc.getLocation().getName();
        this.config = "npc";
        this.subActions = new ArrayList<>();
        this.actionSubject = npc.getName();
        initialize(npc);
    }

    /**
     * Go to enemy location
     *
     * @param enemy - enemy to go to
     */
    public Goto(Enemy enemy) {
        this.actionName = Actions.GOTO;
        this.actionDescription = "Go to the " + enemy.getLocation().getName();
        this.config = "enemy";
        this.subActions = new ArrayList<>();
        this.actionSubject = enemy.getName();
        initialize(enemy);
    }

    /**
     * Go to location
     *
     * @param location - location to go to
     */
    public Goto(Location location) {
        this.actionName = Actions.GOTO;
        this.actionDescription = "Go to the " + location.getName();
        this.config = "location";
        this.subActions = new ArrayList<>();
        this.actionSubject = location.getName();
        // No initialize for location since you simply need to goto the location
    }

    /**
     * Add any possible sub actions before going to npc
     *
     * @param npc - npc to go to
     */
    private void initialize(NPC npc) {
        // Add all the different ways the quest can go with an NPC
        List<String[]> questPatterns = new ArrayList<>();
        // Learn about npc first before Goto npc
        questPatterns.add(new String[]{Actions.LEARN});
        // Just Goto npc
        questPatterns.add(new String[]{});

        Random random = new Random();
        // Choose a random questPattern and add subActions
        for (String action : questPatterns.get(random.nextInt(questPatterns.size()))) {
            if (action.equals(Actions.LEARN)) {
                subActions.add(new Learn(npc));
            }
        }


    }

    /**
     * Add any sub quests before going to enemy
     *
     * @param enemy
     */
    private void initialize(Enemy enemy) {
        // Add all the different ways the quest can go with an Enemy
        List<String[]> questPatterns = new ArrayList<>();
        // Learn about enemy first before Goto enemy
        questPatterns.add(new String[]{Actions.LEARN});
        // just Goto enemy
        questPatterns.add(new String[]{});

        Random random = new Random();
        // Choose a random questPattern and add subActions
        for (String action : questPatterns.get(random.nextInt(questPatterns.size()))) {
            if (action.equals(Actions.LEARN)) {
                subActions.add(new Learn(enemy));
            }
        }
    }

}
