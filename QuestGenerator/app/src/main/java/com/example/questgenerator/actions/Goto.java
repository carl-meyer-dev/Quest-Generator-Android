package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;

public class Goto extends Action {

    public Goto(NPC npc){
        this.actionText = "Go to " + npc.location.name;
        this.subActions = new ArrayList<>();
        initialize(npc);
    }

    public Goto(Enemy enemy){
        this.actionText = "Go to " + enemy.location.name;
        this.subActions = new ArrayList<>();
        initialize(enemy);
    }

    public Goto(Location location){
        this.actionText = "Goto " + location.name;
        this.subActions = new ArrayList<>();
    }

    public void initialize(NPC npc){
        // Todo: quest generator
    }

    public void initialize(Enemy enemy){
        // Todo: quest generator
    }

}
