package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Item;

import java.util.ArrayList;

public class Steal extends Action {

    public Steal(Item item, Enemy enemy){
        this.actionText = "Steal " + item.name + " from " + enemy.name;
        this.subActions = new ArrayList<>();
        initialize(item, enemy);
    }

    public void initialize(Item item, Enemy enemy){
        // Todo: Quest Generator
    }

}
