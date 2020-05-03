package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;

public class Defend extends Action {
    public Defend(NPC npc, String condition, int duration){
        this.actionText = "Defend " + npc.name;
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize(){

    }
}
