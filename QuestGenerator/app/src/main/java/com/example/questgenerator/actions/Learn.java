package com.example.questgenerator.actions;

import com.example.questgenerator.generator.QuestGenerator;
import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;
import com.example.questgenerator.utils.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Learn extends Action {

    private Random random = new Random();
    private QuestGenerator questGenerator;

    /**
     * Learn about a npc
     *
     * @param npc - NPC to learn about
     */
    public Learn(NPC npc) {
        this.actionText = "Learn about " + npc.name;
        this.subActions = new ArrayList<>();
        questGenerator = QuestGenerator.getInstance();
        initialize(npc);

    }

    /**
     * Learn about an enemy
     *
     * @param enemy - enemy to learn about
     */
    public Learn(Enemy enemy) {
        this.actionText = "Learn about " + enemy.name;
        this.subActions = new ArrayList<>();
        questGenerator = QuestGenerator.getInstance();
        initialize(enemy);
    }

    /**
     * Learn about a location
     *
     * @param location - location to learn about
     */
    public Learn(Location location) {
        this.actionText = "Learn about " + location.name;
        this.subActions = new ArrayList<>();
        questGenerator = QuestGenerator.getInstance();
        initialize(location);
    }

    /**
     * Get random questPattern and assign subActions
     *
     * @param npc the npc that you learn info from.
     *            Note this might need to be reworked since npc isn't actually used.
     */
    public void initialize(NPC npc) {

        String[] randomlySelectedPattern = getRandomQuestPattern();

        this.subActions = questGenerator.assignActions(randomlySelectedPattern);

    }

    /**
     * Get random questPattern and assign subActions
     *
     * @param enemy the enemy that you learn info from.
     *              Note this might need to be reworked since enemy isn't actually used.
     */
    public void initialize(Enemy enemy) {
        String[] randomlySelectedPattern = getRandomQuestPattern();

        this.subActions = questGenerator.assignActions(randomlySelectedPattern);
    }

    /**
     * Get random questPattern and assign subActions
     *
     * @param location the location that you learn info from.
     *                 Note this might need to be reworked since location isn't actually used.
     */
    public void initialize(Location location) {
        String[] randomlySelectedPattern = getRandomQuestPattern();

        this.subActions = questGenerator.assignActions(randomlySelectedPattern);
    }

    /**
     * Since the patterns are the same regardless of NPC, Enemy and Location use this method to
     * get random questPattern
     *
     * @return randomlySelectedPattern
     */
    private String[] getRandomQuestPattern() {
        // Todo: Might have to rework this as listen actions doesn't make sense after learning location

        // Add all the different ways the quest can go
        List<String[]> questPatterns = new ArrayList<>();
        // After Learning npc get a quest to kill a random enemy loot the item the enemy has and use it
        questPatterns.add(new String[]{Actions.KILL, Actions.LOOT, Actions.USE});
        // After learning npc get a quest to get a random item and use it
        questPatterns.add(new String[]{Actions.GET, Actions.USE});
        // After learning npc listen to them
        questPatterns.add(new String[]{Actions.LISTEN});

        return questPatterns.get(random.nextInt(questPatterns.size()));
    }

}
