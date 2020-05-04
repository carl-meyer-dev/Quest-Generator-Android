package com.example.questgenerator.actions;

import com.example.questgenerator.activities.MainActivity;
import com.example.questgenerator.generator.QuestGenerator;
import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.utils.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Get extends Action {

    MainActivity activity;

    /**
     * Get an item
     * @param item - item to get
     * @param activity - reference to main activity
     */
    public Get(Item item, MainActivity activity) {
        this.activity = activity;
        this.actionText = "Aquire " + item.name;
        this.subActions = new ArrayList<>();
        initialize(item);
    }

    /**
     * Add any sub quests before getting item
     * @param item - item to get
     */
    public void initialize(Item item) {
        QuestGenerator questGenerator = QuestGenerator.getInstance(activity);
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
                    subActions.add(new Steal(item, questGenerator.getEnemy(item), activity));
                    break;
                case Actions.LEARN:
                    subActions.add(new Learn(questGenerator.getEnemy(item), activity));
                    break;
                case Actions.GOTO:
                    subActions.add(new Goto(questGenerator.getLocation(), activity));
                    break;
                case Actions.GATHER:
                    subActions.add(new Gather(item));
                    break;
            }
        }
    }
}
