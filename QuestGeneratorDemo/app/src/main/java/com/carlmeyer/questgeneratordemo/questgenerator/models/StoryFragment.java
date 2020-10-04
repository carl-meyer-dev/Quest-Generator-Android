package com.carlmeyer.questgeneratordemo.questgenerator.models;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StoryFragment extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String motivation;
    private String description;
    public RealmList<String> actions;
    public RealmList<String> dialogKeys;
    public String questDialog;
    public String questCompleteDialog;

    public StoryFragment(int id, String motivation, String description, String[] actions, String questDialog, String questCompleteDialog, String[] dialogKeys) {
        this.id = id;
        this.motivation = motivation;
        this.description = description;
        this.actions = getRealmListOfActions(actions);
        this.questDialog = questDialog;
        this.questCompleteDialog = questCompleteDialog;
        this.dialogKeys = getRealmListOfDialogKeys(Arrays.asList(dialogKeys));
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

    public void setActions(List<DBAction> actions) {
        this.actions = getRealmListOfActions(actions);
    }

    private RealmList<String> getRealmListOfActions(List<DBAction> actions) {
        RealmList<String> realmListOfActions = new RealmList<>();

        for (DBAction action : actions) {
            String stringAction = action.getAction();
            if (action.getConfig() != null) {
                stringAction = stringAction + "-" + action.getConfig();
            }
            realmListOfActions.add(stringAction);
            Log.d("Test", stringAction);
        }

        return realmListOfActions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private RealmList<String> getRealmListOfActions(String[] actions) {

        RealmList<String> realmListOfActions = new RealmList<>();

        realmListOfActions.addAll(Arrays.asList(actions));

        return realmListOfActions;
    }

    private String[] getStringArrayOfActions(RealmList<String> actions) {
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

    public RealmList<String> getDialogKeys() {
        return dialogKeys;
    }

    public void setDialogKeys(List<String> dialogKeys) {
        this.dialogKeys = getRealmListOfDialogKeys(dialogKeys);
    }

    private RealmList<String> getRealmListOfDialogKeys(List<String> dialogKeys) {

        RealmList<String> realmListOfDialogKeys = new RealmList<>();

        realmListOfDialogKeys.addAll(dialogKeys);

        return realmListOfDialogKeys;
    }

    public void setDialogKeys(RealmList<String> dialogKeys) {
        this.dialogKeys = dialogKeys;
    }

    public String getQuestCompleteDialog() {
        return questCompleteDialog;
    }

    public void setQuestCompleteDialog(String questCompleteDialog) {
        this.questCompleteDialog = questCompleteDialog;
    }
}
