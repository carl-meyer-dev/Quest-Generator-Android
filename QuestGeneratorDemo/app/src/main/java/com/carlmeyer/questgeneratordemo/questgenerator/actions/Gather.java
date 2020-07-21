package com.carlmeyer.questgeneratordemo.questgenerator.actions;

import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;

import java.util.ArrayList;

public class Gather extends Action {

    /**
     * Gather the item
     *
     * @param item - item to gather
     */
    public Gather(Item item) {
        this.actionText = "gather a " + item.getName();
        this.subActions = new ArrayList<>();
        initialize();
    }

    public void initialize() {

    }

}
