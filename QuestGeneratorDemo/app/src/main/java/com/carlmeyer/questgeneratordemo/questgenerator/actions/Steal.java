package com.carlmeyer.questgeneratordemo.questgenerator.actions;


import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestGenerator;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Steal extends Action {


    /**
     * Steal and item from an enemy
     *
     * @param item  - item to steal
     * @param enemy - enemy to steal the item from
     */
    public Steal(Item item, Enemy enemy) {
        this.actionName = Actions.STEAL;
        this.actionDescription = "Steal " + item.getName() + " from the " + enemy.getName();
        this.config = "item from enemy";
        this.subActions = new ArrayList<>();
        initialize(item, enemy);
    }

    /**
     * Add any sub quests before stealing the item
     *
     * @param item  - item to steal
     * @param enemy - enemy to steal from
     */
    private void initialize(Item item, Enemy enemy) {
        QuestGenerator questGenerator = QuestGenerator.getInstance();
        // Add possible ways quest can go
        List<String[]> questPatterns = new ArrayList<>();
        // Goto enemy location before stealing item
        questPatterns.add(new String[]{Actions.GOTO});
        // Just steal the item
        questPatterns.add(new String[]{});

        Random random = new Random();

        for (String action : questPatterns.get(random.nextInt(questPatterns.size()))) {
            if (action.equals(Actions.GOTO)) {
                subActions.add(new Goto(enemy.getLocation()));
            }
        }


    }

}
