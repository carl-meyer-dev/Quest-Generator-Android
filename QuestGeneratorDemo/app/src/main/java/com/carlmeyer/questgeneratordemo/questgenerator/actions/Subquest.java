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
        initialize();
    }

    private void initialize() {

    }

}
