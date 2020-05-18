package com.carlmeyer.questgeneratordemo.questgenerator.models;

import java.util.List;

public class Action {

    // This is an abstract action that will be the basis for all actions
    // The player needs to perform these actions to complete the quest.

    public List<Action> subActions;
    public String actionText;
    public boolean complete = false;

    public String getActionText() {
        return actionText;
    }

    public List<Action> getSubActions() {
        return subActions;
    }
}
