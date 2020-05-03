package com.example.questgenerator.generator;

import android.util.Log;

import com.example.questgenerator.actions.Get;
import com.example.questgenerator.actions.Goto;
import com.example.questgenerator.actions.Kill;
import com.example.questgenerator.actions.Learn;
import com.example.questgenerator.actions.Listen;
import com.example.questgenerator.actions.Report;
import com.example.questgenerator.actions.Steal;
import com.example.questgenerator.actions.Subquest;
import com.example.questgenerator.actions.Use;
import com.example.questgenerator.activities.MainActivity;
import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;
import com.example.questgenerator.models.Quest;
import com.example.questgenerator.models.StoryFragment;
import com.example.questgenerator.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestGenerator {

    // Reference to main activity
    MainActivity activity;

    // Local copies of all data
    List<Location> locations;
    List<NPC> npcs;
    List<Enemy> enemies;
    List<Item> items;
    Random random = new Random();

    private static QuestGenerator instance;

    public static QuestGenerator getInstance(MainActivity activity) {
        if (instance == null) {
            instance = new QuestGenerator(activity);
        }
        return instance;
    }

    private QuestGenerator(MainActivity activity) {
        this.activity = activity;
    }

    public Quest getQuest(String motive, int minimumComplexity) {
        int failCatch = 0;
        Subquest root = getActions(motive);
        Quest quest = new Quest(root);

        while (quest.getDepth() < minimumComplexity || quest.getDepth() > 20) {
            if (failCatch > 100) {
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
    public Subquest getActions(String motive) {
        // load the list of available npcs, enemies and locations
        locations = activity.locations;
        npcs = activity.npcs;
        enemies = activity.enemies;
        items = activity.items;

        StoryFragment storyFragment = new StoryFragment();
        Random random = new Random();

        if(motive.equals(Constants.KNOWLEDGE)){
            storyFragment = activity.knowledgeStoryFragments.get(
                    random.nextInt(activity.knowledgeStoryFragments.size())
            );
        }else if(motive.equals(Constants.COMFORT)){
            storyFragment = activity.comfortStoryFragments.get(
                    random.nextInt(activity.comfortStoryFragments.size())
            );
        }else if (motive.equals(Constants.JUSTICE)){
            storyFragment = activity.justiceStoryFragments.get(
                    random.nextInt(activity.justiceStoryFragments.size())
            );
        }else {
            // Not implemented
        }

        List<Action> rootActions = assignActions(new ArrayList<>(storyFragment.actions));

        Subquest root = new Subquest(rootActions);

        root.actionText = storyFragment.description;

        return root;

    }

    // Turns a string action plan into into a list of Actions with appropriate data
    public List<Action> assignActions(List<String> actions){
        // reverse the list so the plan can be made regressively
        List<String> reversedActions = new ArrayList<>(actions);
        Collections.reverse(reversedActions);

        NPC npcToReportTo = null;
        Enemy enemyToAttack = null;
        Item itemToAcquire = null;
        Item itemToUse = null;

        List<Action> rootActions = new ArrayList<>();

        for (String action : reversedActions) {
            switch (action) {
                case Constants.GET:
                    if (itemToUse != null) {
                        rootActions.add(new Get(itemToUse));
                        itemToUse = null;
                    } else {
                        rootActions.add(new Get(getItem()));
                    }
                    break;
                case Constants.GOTO:
                    if (npcToReportTo != null) {
                        // An npc was selected to report to, go to them
                        rootActions.add(new Goto(npcToReportTo));
                        npcToReportTo = null;
                    } else if (enemyToAttack != null) {
                        // An enemy was selected to attack, get his location
                        rootActions.add(new Goto(enemyToAttack));
                        enemyToAttack = null;
                    } else {
                        // Default to a random location
                        rootActions.add(new Goto(getLocation()));
                    }
                    break;
                case Constants.USE:
                    itemToUse = getItem();
                    rootActions.add(new Use(itemToUse));
                    break;
                case Constants.STEAL:
                    rootActions.add(new Steal(getItem(), getEnemy()));
                    break;
                case Constants.LEARN:
                    rootActions.add(new Learn(getNPC()));
                    break;
                case Constants.REPORT:
                    npcToReportTo = getNPC();
                    rootActions.add(new Report(npcToReportTo));
                    break;
                case Constants.KILL:
                    enemyToAttack = getEnemy();
                    rootActions.add(new Kill(enemyToAttack));
                    break;
                case Constants.LISTEN:
                    rootActions.add(new Listen(getNPC()));
                    break;
            }
        }

        // reverse root actions list that was built backwards
        Collections.reverse(rootActions);

        return rootActions;

    }

    // Get random enemy
    private Enemy getEnemy() {
        int index = random.nextInt(enemies.size() -1);
        Enemy randomEnemy = enemies.get(index);
        return randomEnemy;
    }

    // Get enemy with specific item
    private Enemy getEnemy(Item item){
        Enemy enemy = null;

        // Search All Enemies for one that has this item
        for (Enemy e : enemies) {
            for (Item i : items) {
                if (i.name.equals(item.name)){
                    enemy = e;
                    break;
                }
            }
        }

        // If no enemy has the item, get a random one.
        if(enemy == null){
            enemy = getEnemy();
        }

        return enemy;
    }

    // Get random location
    private Location getLocation() {
        int index = random.nextInt(locations.size() -1);
        Location randomLocation = locations.get(index);
        return randomLocation;
    }

    // Get random item
    private Item getItem() {
        int index = random.nextInt(items.size() - 1);
        Item randomItem = items.get(index);
        return randomItem;
    }

    // Get random NPC
    private NPC getNPC(){
        int index = random.nextInt(npcs.size() -1);
        NPC randomNPC = npcs.get(index);
        return randomNPC;
    }


}
