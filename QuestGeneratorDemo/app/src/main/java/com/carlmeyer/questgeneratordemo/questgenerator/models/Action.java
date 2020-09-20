package com.carlmeyer.questgeneratordemo.questgenerator.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;

public class Action implements RealmModel {

    // This is an abstract action that will be the basis for all actions
    // The player needs to perform these actions to complete the quest.

    public String actionName; // the name of the action
    public String actionDescription; // the description of the action
    public String actionDialog; // the dialog for a particular action (note not implemented fully yet)
    public String config; // the configuration of the action (does it relate to a npc, enemy, or location?)
    public List<Action> subActions; // sub actions that need to be completed before you can complete this action.


}
