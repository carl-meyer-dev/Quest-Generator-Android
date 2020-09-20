package com.carlmeyer.questgeneratordemo.questgenerator.data;

import com.carlmeyer.questgeneratordemo.questgenerator.actions.Get;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class Actions {
    // The possible actions for the quest generator algorithm
    public static final String GET = "get";
    public static final String GOTO = "goto";
    public static final String USE = "use";
    public static final String STEAL = "steal";
    public static final String LEARN = "learn";
    public static final String REPORT = "report";
    public static final String KILL = "kill";
    public static final String LISTEN = "listen";
    public static final String GATHER = "gather";
    public static final String LOOT = "loot";
    public static final String SUBQUEST = "subquest";
    /* list of predefined DB Actions that we will use to seed the DB. Note that I added a list of
     of options to each action to define the different parameters for each action. */
    DBAction Get = new DBAction(1, GET, new String[]{"item"});
    DBAction Goto = new DBAction(2, GOTO, new String[]{"location", "npc", "enemy"});
    DBAction Use = new DBAction(3, USE, new String[]{"item"});
    DBAction Steal = new DBAction(4, STEAL, new String[]{"item from enemy"});
    DBAction Learn = new DBAction(5, LEARN, new String[]{"enemy", "npc", "location"});
    DBAction Report = new DBAction(6, REPORT, new String[]{"npc"});
    DBAction Kill = new DBAction(7, KILL, new String[]{"enemy"});
    DBAction Listen = new DBAction(8, LISTEN, new String[]{"npc", "enemy"});
    DBAction Gather = new DBAction(9, GATHER, new String[]{"item"});
    DBAction Loot = new DBAction(10, LOOT, new String[]{"item"});

    /**
     * Get a string list of possible actions
     * (this was initially used to seed the DB but I use getDBActions now instead
     * @return
     */
    public List<String> getActions() {
        List<String> actions = new ArrayList<>();

        actions.add(GET);
        actions.add(GOTO);
        actions.add(USE);
        actions.add(STEAL);
        actions.add(LEARN);
        actions.add(REPORT);
        actions.add(KILL);
        actions.add(LISTEN);
        actions.add(GATHER);
        actions.add(LOOT);

        return actions;
    }

    /**
     * Get a list of all the DBActions that we need to add to the DB when seeding the App for the
     * first time
     * @return list of DB Actions
     */
    public List<DBAction> getDBActions(){
        List<DBAction> dbActions = new ArrayList<>();

        dbActions.add(Get);
        dbActions.add(Goto);
        dbActions.add(Use);
        dbActions.add(Steal);
        dbActions.add(Learn);
        dbActions.add(Report);
        dbActions.add(Kill);
        dbActions.add(Listen);
        dbActions.add(Gather);
        dbActions.add(Loot);

        return dbActions;
    }
}
