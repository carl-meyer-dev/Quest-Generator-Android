package com.example.questgenerator.actions;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Item;

import java.util.ArrayList;

public class Gather extends Action {

    /**
     * Gather the item
     *
     * @param item - item to gather
     */
    public Gather(Item item) {
        this.actionText = "Gather " + item.getName();
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize() {

    }

}
