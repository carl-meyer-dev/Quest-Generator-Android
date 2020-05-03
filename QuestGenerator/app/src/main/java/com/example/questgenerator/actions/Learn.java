package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;

public class Learn extends Action {

    public Learn(NPC npc){
        this.actionText = "Learn about " + npc.name;
        this.subActions = new ArrayList<>();
        initialize(npc);

    }

    public Learn(Enemy enemy){
        this.actionText = "Learn about " + enemy.name;
        this.subActions = new ArrayList<>();
        initialize(enemy);
    }

    public Learn(Location location){
        this.actionText = "Learn about " + location.name;
        this.subActions = new ArrayList<>();
        initialize(location);
    }

    public void initialize(NPC npc){
        // Todo: Quest Generator
    }

    public void initialize(Enemy enemy){
        // Todo: Quest Generator
    }

    public void initialize(Location location){
        // Todo: Quest Generator
    }

}
