package com.carlmeyer.questgeneratordemo.questgenerator.actions;


import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;

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
