package com.example.questgenerator.actions;

import com.example.questgenerator.activities.MainActivity;
import com.example.questgenerator.generator.QuestGenerator;
import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.utils.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Use extends Action  {

    private MainActivity activity;

    /**
     * Use an item
     * @param item - item to use
     * @param activity - reference to main activity
     */
    public Use(Item item, MainActivity activity){
        this.activity = activity;
        this.actionText = "Use " + item.name;
        this.subActions = new ArrayList<>();
        initialize(item);
    }

    /**
     * Add any sub quests before using item
     * @param item
     */
    public void initialize(Item item){
        QuestGenerator questGenerator = QuestGenerator.getInstance(activity);
        // Add possible ways this quest can go
        List<String[]> questPatterns = new ArrayList<>();
        // Kill an the enemy that has the item and loot that item before using it
        questPatterns.add(new String[]{Actions.KILL, Actions.LOOT});
        // Just use the item
        questPatterns.add(new String[]{});

        Random random = new Random();

        for (String action : questPatterns.get(random.nextInt())){
            // TODO: This needs to be fixed, no goto in quest patterns
            if(action.equals(Actions.GOTO)){
                subActions.add(new Goto(questGenerator.getLocation(), activity));
            }else if(action.equals(Actions.LOOT)){
                subActions.add(new Loot(item));
            }else if(action.equals(Actions.KILL)){
                subActions.add(new Kill(questGenerator.getEnemy(item)));
            }
        }

    }

}
