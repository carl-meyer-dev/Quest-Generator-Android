package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Item;

import java.util.ArrayList;

public class Loot extends Action {

    /**
     * Loot an item
     * @param item - item to loot
     */
    public Loot(Item item){
        this.actionText = "Loot " + item.name;
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize(){

    }

}
