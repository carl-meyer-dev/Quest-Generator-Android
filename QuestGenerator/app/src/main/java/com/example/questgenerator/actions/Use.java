package com.example.questgenerator.actions;

import com.example.questgenerator.generator.QuestGenerator;
import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.utils.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Use extends Action {


    /**
     * Use an item
     *
     * @param item - item to use
     */
    public Use(Item item) {
        this.actionText = "Use " + item.name;
        this.subActions = new ArrayList<>();
        initialize(item);
    }

    /**
     * Add any sub quests before using item
     *
     * @param item
     */
    public void initialize(Item item) {
        QuestGenerator questGenerator = QuestGenerator.getInstance();
        // Add possible ways this quest can go
        List<String[]> questPatterns = new ArrayList<>();
        // Kill an the enemy that has the item and loot that item before using it
        questPatterns.add(new String[]{Actions.KILL, Actions.LOOT});
        // Just use the item
        questPatterns.add(new String[]{});

        Random random = new Random();

        for (String action : questPatterns.get(random.nextInt(questPatterns.size()))) {
            // TODO: This needs to be fixed, no goto in quest patterns
            switch (action) {
                case Actions.GOTO:
                    subActions.add(new Goto(questGenerator.getLocation()));
                    break;
                case Actions.LOOT:
                    subActions.add(new Loot(item));
                    break;
                case Actions.KILL:
                    subActions.add(new Kill(questGenerator.getEnemy(item)));
                    break;
            }
        }

    }

}
