package com.example.questgenerator.actions;

import android.view.animation.LinearInterpolator;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;

public class Listen extends Action {

    public Listen(NPC npc){
        this.actionText = "Listen to " + npc.name;
        this.subActions = new ArrayList<>();
        initialize();
    }

    public Listen(Enemy enemy){
        this.actionText = "Listen to " + enemy.name;
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize(){

    }


}
