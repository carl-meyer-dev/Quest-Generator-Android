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
        this.actionText = "Listen to " + npc.getName();
        this.subActions = new ArrayList<>();
        initialize();
    }

    /**
     * Listen to an enemy
     *
     * @param enemy - enemy to listen to
     */
    public Listen(Enemy enemy) {
        this.actionText = "Listen to " + enemy.getName();
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize() {
        // no further subActions to add to rootQuest.
    }


}
