package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Item;

import java.util.ArrayList;

public class Use extends Action  {

    public Use(Item item){
        actionText = "Use " + item.name;
        subActions = new ArrayList<>();
        initialize();
    }

    public void initialize(){
        // Todo: Quest Generator
    }

}
