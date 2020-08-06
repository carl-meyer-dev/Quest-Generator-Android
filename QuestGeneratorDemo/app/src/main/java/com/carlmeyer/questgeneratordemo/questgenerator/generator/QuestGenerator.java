package com.carlmeyer.questgeneratordemo.questgenerator.generator;


import android.util.Log;

import com.carlmeyer.questgeneratordemo.questgenerator.actions.Get;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Goto;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Kill;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Learn;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Listen;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Report;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Steal;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Subquest;
import com.carlmeyer.questgeneratordemo.questgenerator.actions.Use;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;
import com.carlmeyer.questgeneratordemo.questgenerator.data.StoryFragments;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Quest;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.realm.Realm;

public class QuestGenerator {

    private Random random = new Random();
    private StoryFragments storyFragments = new StoryFragments();

    // Local copies of all data
    private List<Location> locations;
    private List<NPC> npcs;
    private List<Enemy> enemies;
    private List<Item> items;
    // load available story fragments
    private List<StoryFragment> knowledgeStoryFragments = storyFragments.getKnowledgeStoryFragments();
    private List<StoryFragment> comfortStoryFragments = storyFragments.getComfortFragments();
    private List<StoryFragment> justiceStoryFragments = storyFragments.getJusticeStoryFragments();


    private static QuestGenerator instance;

    public static QuestGenerator getInstance() {
        if (instance == null) {
            instance = new QuestGenerator();
        }
        return instance;
    }

    private QuestGenerator() {
        // load the list of available npcs, enemies and locations
        Realm realm = Realm.getDefaultInstance();
        locations = realm.where(Location.class).findAll();
        npcs = realm.where(NPC.class).findAll();
        enemies = realm.where(Enemy.class).findAll();
        items = realm.where(Item.class).findAll();
        knowledgeStoryFragments = realm.where(StoryFragment.class).equalTo("motive", "knowledge").findAll();
        comfortStoryFragments = realm.where(StoryFragment.class).equalTo("motive", "comfort").findAll();
        justiceStoryFragments = realm.where(StoryFragment.class).equalTo("motive", "justice").findAll();
        logStoryFragments();
    }

    private void logStoryFragments() {
        Log.d("Story Fragments From DB", "knowledge Story Fragments:");
        for (StoryFragment sf : knowledgeStoryFragments){
            Log.d("Knowledge Story Fragment", sf.getMotive() + " : " + sf.getDescription());
        }
        for (StoryFragment sf : comfortStoryFragments){
            Log.d("Knowledge Story Fragment", sf.getMotive() + " : " + sf.getDescription());
        }
        for (StoryFragment sf : justiceStoryFragments){
            Log.d("Knowledge Story Fragment", sf.getMotive() + " : " + sf.getDescription());
        }
    }

    private Quest getQuestFromStoryFragment(StoryFragment storyFragment){
        // get the list of root actions provided by the story fragment
        List<Action> rootActions = assignActions(storyFragment.getActions());

        // create the root of the quest
        Subquest root = new Subquest(rootActions);

        root.actionText = storyFragment.getDescription();

        //create the quest with the root actions and the story fragment ID that will be used later for generating the dialog

        return new Quest(root,storyFragment);
    }

    public Quest getQuest(String motive, int minimumComplexity) {
        int failCatch = 0;
        // get the story fragment we are going to use for the quest
        StoryFragment storyFragment = getActions(motive);

       Quest quest = getQuestFromStoryFragment(storyFragment);

        while (quest.getDepth() < minimumComplexity || quest.getDepth() > 20) {
            if (failCatch > 100) {
                Log.d("Error", "Can't find path, breaking out.");
                break;
            }

            storyFragment = getActions(motive);

            quest = getQuestFromStoryFragment(storyFragment);
            failCatch++;
        }

        // Will add quest meta data here, such as text, title, etc
        return quest;
    }

    // Get the appropriate actions for the subquest based on motive
    private StoryFragment getActions(String motive) {


        StoryFragment storyFragment = new StoryFragment();
        Random random = new Random();

        switch (motive) {
            case Motives.KNOWLEDGE:
                storyFragment = knowledgeStoryFragments.get(
                        random.nextInt(knowledgeStoryFragments.size())
                );
                break;
            case Motives.COMFORT:
                storyFragment = comfortStoryFragments.get(
                        random.nextInt(comfortStoryFragments.size())
                );
                break;
            case Motives.JUSTICE:
                storyFragment = justiceStoryFragments.get(
                        random.nextInt(justiceStoryFragments.size())
                );
                break;
            default:
                // Not implemented
                break;
        }



        return storyFragment;

    }

    // Turns a string action plan into into a list of Actions with appropriate data
    public List<Action> assignActions(String[] actions) {
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
                        rootActions.add(new Get(itemToUse));
                        itemToUse = null;
                    } else {
                        rootActions.add(new Get(getItem()));
                    }
                    break;
                case Actions.GOTO:
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
                case Actions.USE:
                    itemToUse = getItem();
                    rootActions.add(new Use(itemToUse));
                    break;
                case Actions.STEAL:
                    rootActions.add(new Steal(getItem(), getEnemy()));
                    break;
                case Actions.LEARN:
                    rootActions.add(new Learn(getNPC()));
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
        if (enemies.size() > 1) {
            int index = random.nextInt(enemies.size() - 1);
            return enemies.get(index);
        } else {
            return enemies.get(0);
        }
    }

    // Get enemy with specific item
    public Enemy getEnemy(Item item) {
        Enemy enemy = null;

        // Search All Enemies for one that has this item
        for (Enemy e : enemies) {
            for (Item i : items) {
                if (i.getName().equals(item.getName())) {
                    enemy = e;
                    break;
                }
            }
        }

        // If no enemy has the item, get a random one.
        if (enemy == null) {
            enemy = getEnemy();
        }

        return enemy;
    }

    // Get random location
    public Location getLocation() {
        if (locations.size() > 1) {
            int index = random.nextInt(locations.size() - 1);
            return locations.get(index);
        } else {
            return locations.get(0);
        }
    }

    // Todo: getLocation(Item item)
    // get location of item
    // maybe add location field to item
    // an items location is where it is in the game world
    // if an enemy has the item. the item location is the location of the enemy that has the item

    // Get random item
    public Item getItem() {
        if (items.size() > 1) {
            int index = random.nextInt(items.size() - 1);
            return items.get(index);
        } else {
            return items.get(0);
        }
    }

    // Get random NPC
    public NPC getNPC() {
        if (npcs.size() > 1) {
            int index = random.nextInt(npcs.size() - 1);
            return npcs.get(index);
        } else {
            return npcs.get(0);
        }
    }


}
