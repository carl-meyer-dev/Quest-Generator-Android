package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;

import java.util.ArrayList;

public class Kill extends Action {

    public Kill(Enemy enemy){
        this.actionText = "Kill " + enemy.name;
        this.subActions = new ArrayList<>();
        initialize(enemy);
    }

    public void initialize(Enemy enemy){
        // Todo: Quest Generator
    }

}
