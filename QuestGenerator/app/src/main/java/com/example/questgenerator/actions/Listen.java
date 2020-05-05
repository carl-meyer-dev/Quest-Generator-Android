package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;

public class Listen extends Action {

    /**
     * Listen to an npc
     *
     * @param npc - npc to listen to
     */
    public Listen(NPC npc) {
        this.actionText = "Listen to " + npc.name;
        this.subActions = new ArrayList<>();
        initialize();
    }

    /**
     * Listen to an enemy
     *
     * @param enemy - enemy to listen to
     */
    public Listen(Enemy enemy) {
        this.actionText = "Listen to " + enemy.name;
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize() {
        // no further subActions to add to rootQuest.
    }


}
