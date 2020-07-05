package com.carlmeyer.questgeneratordemo.questgenerator.actions;

import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;

import java.util.ArrayList;

public class Loot extends Action {

    /**
     * Loot an item
     *
     * @param item - item to loot
     */
    public Loot(Item item) {
        this.actionText = "Loot " + item.getName();
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize() {

    }

}
