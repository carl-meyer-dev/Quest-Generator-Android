package com.carlmeyer.questgeneratordemo.questgenerator.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StoryFragment extends RealmObject {

    @PrimaryKey
    private int id;
    private String motivation;
    private String description;
    public RealmList<String> actions;
    public String questDialog;

    public StoryFragment(int id, String motivation, String description, String[] actions) {
        this.id = id;
        this.motivation = motivation;
        this.description = description;
        this.actions = getRealmListOfActions(actions);
    }

    public StoryFragment() {

    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getActions() {
        return getStringArrayOfActions(actions);
    }

    public void setActions(String[] actions) {
        this.actions = getRealmListOfActions(actions);
    }

    public void setActions(List<DBAction> actions){
        this.actions = getRealmListOfActions(actions);
    }

    private RealmList<String> getRealmListOfActions(List<DBAction> actions) {
        RealmList<String> realmListOfActions = new RealmList<>();

        for (DBAction action : actions){
            realmListOfActions.add(action.getAction());
            //TODO: need to take configuration into account
        }

        return realmListOfActions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private RealmList<String> getRealmListOfActions(String[] actions){

        RealmList<String> realmListOfActions = new RealmList<>();

        realmListOfActions.addAll(Arrays.asList(actions));

        return realmListOfActions;
    }

    private String[] getStringArrayOfActions(RealmList<String> actions){
        String[] stringArrayOfActions = new String[actions.size()];
        for (int i = 0; i < actions.size(); i++) {
            stringArrayOfActions[i] = actions.get(i);
        }
        return stringArrayOfActions;
    }

    public void setActions(RealmList<String> actions) {
        this.actions = actions;
    }

    public String getQuestDialog() {
        return questDialog;
    }

    public void setQuestDialog(String questDialog) {
        this.questDialog = questDialog;
    }
}
