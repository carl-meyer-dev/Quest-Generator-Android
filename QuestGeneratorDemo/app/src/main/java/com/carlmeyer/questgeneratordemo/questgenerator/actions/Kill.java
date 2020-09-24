package com.carlmeyer.questgeneratordemo.questgenerator.actions;


import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Kill extends Action {

    /**
     * Kill an enemy
     *
     * @param enemy - enemy to kill
     */
    public Kill(Enemy enemy) {
        this.actionName = Actions.KILL;
        this.actionDescription = "Kill a " + enemy.getName();
        this.config = "enemy";
        this.subActions = new ArrayList<>();
        this.actionSubject = enemy.getName();
        initialize(enemy);
    }

    /**
     * Get random questPattern and assign subActions
     *
     * @param enemy - enemy to kill
     */
    private void initialize(Enemy enemy) {
        // Add all the different ways the quest can go
        List<String[]> questPatterns = new ArrayList<>();
        // Listen to the enemy first before killing him
        questPatterns.add(new String[]{Actions.LISTEN});
        // Just Kill the enemy
        questPatterns.add(new String[]{});

        Random random = new Random();

        for (String action : questPatterns.get(random.nextInt(questPatterns.size()))) {
            if (action.equals(Actions.LISTEN)) {
                subActions.add(new Listen(enemy));
            }
        }
    }

}
