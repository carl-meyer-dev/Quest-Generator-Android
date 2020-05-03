package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;

public class Report extends Action {

    public Report(NPC npc){
        this.actionText = "Report to " + npc.name;
        subActions = new ArrayList<>();
        initialize(npc);
    }

    public void initialize(NPC npc){

    }

}
