package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;

public class Defend extends Action {

    // Todo: This Action is never used. Might want to remove it.

    /**
     * Defend NPC for a specific duration or condition met
     *
     * @param npc       - npc to defend
     * @param condition - condition to be met
     * @param duration  - duration to defend
     */
    public Defend(NPC npc, String condition, int duration) {
        this.actionText = "Defend " + npc.getName();
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize() {

    }
}
