package com.carlmeyer.questgeneratordemo.questgenerator.data;

import java.util.ArrayList;
import java.util.List;

public class Actions {
    // Actions
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
}
