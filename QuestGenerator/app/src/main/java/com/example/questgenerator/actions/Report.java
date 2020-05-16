package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;

public class Report extends Action {

    /**
     * Report to an npc
     *
     * @param npc - npc to report to
     */
    public Report(NPC npc) {
        this.actionText = "Report to " + npc.getName();
        subActions = new ArrayList<>();
        initialize(npc);
    }

    public void initialize(NPC npc) {

    }

}
