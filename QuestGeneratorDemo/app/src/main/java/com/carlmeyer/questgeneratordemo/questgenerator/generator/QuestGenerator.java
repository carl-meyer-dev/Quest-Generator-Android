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
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
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

    // Local copies of all data
    private List<Location> locations;
    private List<NPC> npcs;
    private List<Enemy> enemies;
    private List<Item> items;
    // load available story fragments
    private List<StoryFragment> storyFragments;


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
        storyFragments = realm.where(StoryFragment.class).findAll();
        logStoryFragments();
    }

    private void logStoryFragments() {
        for (StoryFragment sf : storyFragments) {
            Log.d("StoryFragments From DB", sf.getMotivation() + " : " + sf.getDescription());
        }
    }

    private Quest getQuestFromStoryFragment(StoryFragment storyFragment) {
        // get the list of root actions provided by the story fragment
        List<Action> rootActions = assignActions(storyFragment.getActions());

        // create the root of the quest
        Subquest root = new Subquest(rootActions);

        root.actionDescription = storyFragment.getDescription();

        //create the quest with the root actions and the story fragment ID that will be used later for generating the dialog

        return new Quest(root, storyFragment);
    }

    public Quest getQuest(Motivation motive, int minimumComplexity) {
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
    private StoryFragment getActions(Motivation motivation) {


        StoryFragment storyFragment = new StoryFragment();
        Random random = new Random();

        List<StoryFragment> storyFragmentsForMotive = motivation.getStoryFragments();

        storyFragment = storyFragmentsForMotive.get(random.nextInt(storyFragmentsForMotive.size()));

        return storyFragment;

    }

    // Turns a string action plan into into a list of Actions with appropriate data
    public List<Action> assignActions(String[] actions) {
        // reverse the list so the plan can be made regressively
        List<String> reversedActions = Arrays.asList(actions);
        Collections.reverse(reversedActions);

        // Todo: This might have to be reworked to include previous NPC, ENEMY, ITEM to maintain coherence or context

        NPC npc = null;
        Enemy enemy = null;
        Item item = null;

        List<Action> rootActions = new ArrayList<>();

        /* get config from string action (something like substring | )
                    goto-location -> go to specific location
                    goto-npc -> go to specific npc
                    goto-enemy -> go to specific enemy
                    goto - > go to random configuration
                */

        for (String action : reversedActions) {
            Log.d("##", "action: " + action);
            String config = "";
            if (action.contains("-")) {
                String[] split = action.split("-", 2);
                action = split[0];
                config = split[1];
            }

            switch (action) {
                case Actions.GET:
                    get(rootActions, item);
                    break;
                case Actions.GOTO:
                    go_to(rootActions, config, npc, enemy);
                    break;
                case Actions.USE:
                    use(rootActions, item);
                    break;
                case Actions.STEAL:
                    steal(rootActions);
                    break;
                case Actions.LEARN:
                    learn(rootActions, config);
                    break;
                case Actions.REPORT:
                    report(rootActions, npc);
                    break;
                case Actions.KILL:
                    kill(rootActions, enemy);
                    break;
                case Actions.LISTEN:
                    listen(rootActions, config, npc, enemy);
                    break;
            }
        }

        // reverse root actions list that was built backwards
        Collections.reverse(rootActions);

        return rootActions; // might have to change type back to String[] just be mindful

    }

    /**
     * Assign Action Methods
     * =============================================================================================
     */
    private void get(List<Action> rootActions, Item item) {
        if (item != null) {
            rootActions.add(new Get(item));
            item = null;
        } else {
            rootActions.add(new Get(getItem()));
        }
    }

    private void go_to(List<Action> rootActions, String config, NPC npc, Enemy enemy) {
        if (config.isEmpty()) {
            if (npc != null) {
                // An npc was selected to report to, go to them
                rootActions.add(new Goto(npc));
                npc = null;
            } else if (enemy != null) {
                // An enemy was selected to attack, get his location
                rootActions.add(new Goto(enemy));
                enemy = null;
            } else {
                // Default to a random location
                rootActions.add(new Goto(getLocation()));
            }
        } else {
            switch (config) {
                case "location":
                    rootActions.add(new Goto(getLocation()));
                    break;
                case "npc":
                    rootActions.add(new Goto(getNPC()));
                    break;
                case "enemy":
                    rootActions.add(new Goto(getEnemy()));
                    break;
            }
        }
    }

    private void use(List<Action> rootActions, Item item) {
        if (item == null) {
            item = getItem();
        }
        rootActions.add(new Use(item));
    }

    private void steal(List<Action> rootActions) {
        rootActions.add(new Steal(getItem(), getEnemy()));
    }

    private void listen(List<Action> rootActions, String config, NPC npc, Enemy enemy) {
        Log.d("##", "Listen");
        if (config.isEmpty()) {
            Log.d("##", "no config");
            if (npc != null) {
                rootActions.add(new Listen(npc));
            } else if (enemy != null) {
                rootActions.add(new Listen(enemy));
            } else {
                if (random.nextBoolean()) {
                    rootActions.add(new Listen(getNPC()));

                } else {
                    rootActions.add(new Listen(getEnemy()));
                }
            }
        } else {
            Log.d("##", "config");

            switch (config) {
                case "npc":
                    rootActions.add(new Listen(getNPC()));
                    Log.d("##", "add listen npc");
                    break;
                case "enemy":
                    rootActions.add(new Listen(getEnemy()));
                    Log.d("##", "add listen enemy");
                    break;
            }
        }
    }

    private void kill(List<Action> rootActions, Enemy enemyToAttack) {
        if (enemyToAttack == null) {
            enemyToAttack = getEnemy();
        }
        rootActions.add(new Kill(enemyToAttack));
    }

    private void report(List<Action> rootActions, NPC npcToReportTo) {
        if (npcToReportTo == null) {
            npcToReportTo = getNPC();
        }
        rootActions.add(new Report(npcToReportTo));
    }

    private void learn(List<Action> rootActions, String config) {
        if (config.isEmpty()) {
            rootActions.add(new Learn(getNPC()));
        } else {
            switch (config) {
                case "location":
                    rootActions.add(new Learn(getLocation()));
                    break;
                case "npc":
                    rootActions.add(new Learn(getNPC()));
                    break;
                case "enemy":
                    rootActions.add(new Learn(getEnemy()));
                    break;
            }
        }
    }

    /**
     * =============================================================================================
     */

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

    // Get random location
    public Location getLocation(NPC npc) {
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
