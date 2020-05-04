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
import com.example.questgenerator.utils.Actions;
import com.example.questgenerator.utils.Motives;

import java.util.ArrayList;
import java.util.Arrays;
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

        switch (motive) {
            case Motives.KNOWLEDGE:
                storyFragment = activity.knowledgeStoryFragments.get(
                        random.nextInt(activity.knowledgeStoryFragments.size())
                );
                break;
            case Motives.COMFORT:
                storyFragment = activity.comfortStoryFragments.get(
                        random.nextInt(activity.comfortStoryFragments.size())
                );
                break;
            case Motives.JUSTICE:
                storyFragment = activity.justiceStoryFragments.get(
                        random.nextInt(activity.justiceStoryFragments.size())
                );
                break;
            default:
                // Not implemented
                break;
        }

        List<Action> rootActions = assignActions(storyFragment.actions);

        Subquest root = new Subquest(rootActions);

        root.actionText = storyFragment.description;

        return root;

    }

    // Turns a string action plan into into a list of Actions with appropriate data
    public List<Action> assignActions(String[] actions){
        // reverse the list so the plan can be made regressively
        List<String> reversedActions = Arrays.asList(actions);
        Collections.reverse(reversedActions);

        // Todo: This might have to be reworked to include previous NPC, ENEMY, ITEM to maintain coherence or context

        NPC npcToReportTo = null;
        Enemy enemyToAttack = null;
        Item itemToAcquire = null;
        Item itemToUse = null;

        List<Action> rootActions = new ArrayList<>();

        for (String action : reversedActions) {
            switch (action) {
                case Actions.GET:
                    if (itemToUse != null) {
                        rootActions.add(new Get(itemToUse, activity));
                        itemToUse = null;
                    } else {
                        rootActions.add(new Get(getItem(), activity));
                    }
                    break;
                case Actions.GOTO:
                    if (npcToReportTo != null) {
                        // An npc was selected to report to, go to them
                        rootActions.add(new Goto(npcToReportTo, activity));
                        npcToReportTo = null;
                    } else if (enemyToAttack != null) {
                        // An enemy was selected to attack, get his location
                        rootActions.add(new Goto(enemyToAttack, activity));
                        enemyToAttack = null;
                    } else {
                        // Default to a random location
                        rootActions.add(new Goto(getLocation(), activity));
                    }
                    break;
                case Actions.USE:
                    itemToUse = getItem();
                    rootActions.add(new Use(itemToUse, activity));
                    break;
                case Actions.STEAL:
                    rootActions.add(new Steal(getItem(), getEnemy(), activity));
                    break;
                case Actions.LEARN:
                    rootActions.add(new Learn(getNPC(), activity));
                    break;
                case Actions.REPORT:
                    npcToReportTo = getNPC();
                    rootActions.add(new Report(npcToReportTo));
                    break;
                case Actions.KILL:
                    enemyToAttack = getEnemy();
                    rootActions.add(new Kill(enemyToAttack));
                    break;
                case Actions.LISTEN:
                    rootActions.add(new Listen(getNPC()));
                    break;
            }
        }

        // reverse root actions list that was built backwards
        Collections.reverse(rootActions);

        return rootActions; // might have to change type back to String[] just be mindful

    }

    // Get random enemy
    public Enemy getEnemy() {
        int index = random.nextInt(enemies.size() -1);
        return enemies.get(index);
    }

    // Get enemy with specific item
    public Enemy getEnemy(Item item){
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
    public Location getLocation() {
        int index = random.nextInt(locations.size() -1);
        return locations.get(index);
    }

    // Todo: getLocation(Item item)
    // get location of item
    // maybe add location field to item
    // an items location is where it is in the game world
    // if an enemy has the item. the item location is the location of the enemy that has the item

    // Get random item
    public Item getItem() {
        int index = random.nextInt(items.size() - 1);
        return items.get(index);
    }

    // Get random NPC
    public NPC getNPC(){
        int index = random.nextInt(npcs.size() -1);
        return npcs.get(index);
    }


}
