package com.carlmeyer.questgeneratordemo.questgenerator.models;

import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StoryFragment extends RealmObject {

    @PrimaryKey
    private int id;
    private String motive;
    private String description;
    public RealmList<String> actions;

    public StoryFragment(int id, String motive, String description, String[] actions) {
        this.id = id;
        this.motive = motive;
        this.description = description;
        this.actions = getRealmListOfActions(actions);


    }

    public StoryFragment() {

    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
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

}
