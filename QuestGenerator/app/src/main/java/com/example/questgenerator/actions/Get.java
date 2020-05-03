package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Item;

import java.util.ArrayList;

public class Get extends Action {

    public Get(Item item){
        this.actionText = "Aquire " + item.name;
        this.subActions = new ArrayList<>();
        initialize(item);

    }

    public void initialize(Item item){
        // QuestGenerator qg
    }

}
