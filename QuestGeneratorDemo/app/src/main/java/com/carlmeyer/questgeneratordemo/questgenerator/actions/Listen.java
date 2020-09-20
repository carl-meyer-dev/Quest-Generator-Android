package com.carlmeyer.questgeneratordemo.questgenerator.actions;


import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;

import java.util.ArrayList;

public class Listen extends Action {

    /**
     * Listen to an npc
     *
     * @param npc - npc to listen to
     */
    public Listen(NPC npc) {
        this.actionName = Actions.LISTEN;
        this.actionDescription = "Listen to " + npc.getName();
        this.config = "npc";
        this.subActions = new ArrayList<>();
        initialize();
    }

    /**
     * Listen to an enemy
     *
     * @param enemy - enemy to listen to
     */
    public Listen(Enemy enemy) {
        this.actionName = Actions.LISTEN;
        this.actionDescription = "Listen to " + enemy.getName();
        this.config = "enemy";
        this.subActions = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        // no further subActions to add to rootQuest.
    }


}
