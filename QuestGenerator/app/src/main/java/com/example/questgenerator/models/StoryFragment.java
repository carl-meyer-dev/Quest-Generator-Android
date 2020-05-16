package com.example.questgenerator.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StoryFragment {

    private int id;
    private String motive;
    private String description;
    private String[] actions;

    public StoryFragment(String motive, String description, String[] actions){
        this.motive = motive;
        this.description = description;
        this.actions = actions;
    }

    public StoryFragment(){

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
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }
}
