package com.carlmeyer.questgeneratordemo.questgenerator.models;

import java.util.List;

import io.realm.RealmModel;

public class Action implements RealmModel {

    // This is an abstract action that will be the basis for all actions
    // The player needs to perform these actions to complete the quest.

    public List<Action> subActions;
    public String actionText;
    public String actionDialog;

}
