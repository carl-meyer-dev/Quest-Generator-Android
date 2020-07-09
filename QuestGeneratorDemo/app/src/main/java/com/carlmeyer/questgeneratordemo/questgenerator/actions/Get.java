package com.carlmeyer.questgeneratordemo.questgenerator.actions;


import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestGenerator;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Get extends Action {


    /**
     * Get an item
     *
     * @param item - item to get
     */
    public Get(Item item) {
        this.actionText = "Acquire a " + item.getName();
        this.subActions = new ArrayList<>();
        initialize(item);
    }

    /**
     * Add any sub quests before getting item
     *
     * @param item - item to get
     */
    public void initialize(Item item) {
        QuestGenerator questGenerator = QuestGenerator.getInstance();
        // Add all the different options that the quest can play out
        List<String[]> questPatterns = new ArrayList<>();
        // Kill an enemy to obtain item
        questPatterns.add(new String[]{Actions.KILL});
        // Steal the item from an enemy
        questPatterns.add(new String[]{Actions.STEAL});
        // Learn about an enemy that has the item and then steal it from them
        questPatterns.add(new String[]{Actions.LEARN, Actions.STEAL});
        // Gather the item
        questPatterns.add(new String[]{Actions.GATHER});

        Random random = new Random();
        // Choose a random questPattern and add subActions
        for (String action : questPatterns.get(random.nextInt(questPatterns.size()))) {
            switch (action) {
                case Actions.KILL:
                    subActions.add(new Kill(questGenerator.getEnemy(item)));
                    break;
                case Actions.STEAL:
                    subActions.add(new Steal(item, questGenerator.getEnemy(item)));
                    break;
                case Actions.LEARN:
                    subActions.add(new Learn(questGenerator.getEnemy(item)));
                    break;
                case Actions.GOTO:
                    subActions.add(new Goto(questGenerator.getLocation()));
                    break;
                case Actions.GATHER:
                    subActions.add(new Gather(item));
                    break;
            }
        }
    }
}
