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
        this.actionName = Actions.LOOT;
        this.actionDescription = "Loot a " + item.getName();
        this.config = "item";
        this.subActions = new ArrayList<>();
        this.actionSubject = item.getName();
        initialize();
    }

    private void initialize() {

    }

}
