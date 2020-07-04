package com.carlmeyer.questgeneratordemo.questgenerator.actions;


import android.util.Log;

import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;

import java.util.ArrayList;
import java.util.List;

public class Subquest extends Action {

    public Subquest(List<Action> subActions) {
        this.actionText = "Subquest complete";
        this.subActions = subActions;
        this.actionType = Actions.SUBQUEST;
        initialize();
    }

    public Subquest() {
        this.actionText = "Subquest complete";
        this.subActions = new ArrayList<>();
    }

    public void initialize() {

    }

}
