package com.carlmeyer.questgeneratordemo.questgenerator.models;

public class StoryFragment {

    private int id;
    private String motive;
    private String description;
    private String[] actions;

    public StoryFragment(int id, String motive, String description, String[] actions) {
        this.id = id;
        this.motive = motive;
        this.description = description;
        this.actions = actions;
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
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
