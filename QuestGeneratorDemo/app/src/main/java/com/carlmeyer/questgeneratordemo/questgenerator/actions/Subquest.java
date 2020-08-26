package com.carlmeyer.questgeneratordemo.questgenerator.actions;


import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;

import java.util.List;

public class Subquest extends Action {

    public Subquest(List<Action> subActions) {
        this.actionName = Actions.SUBQUEST;
        this.actionDescription = "Subquest complete";
        this.config = "sub actions";
        this.subActions = subActions;
        initialize();
    }

    private void initialize() {

    }

}
