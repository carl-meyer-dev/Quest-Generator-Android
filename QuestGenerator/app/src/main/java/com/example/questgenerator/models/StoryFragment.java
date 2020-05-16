package com.example.questgenerator.models;

public class StoryFragment {

    public String motive;
    public String description;
    public String[] actions;

    public StoryFragment(String motive, String description, String[] actions){
        this.motive = motive;
        this.description = description;
        this.actions = actions;
    }

    public StoryFragment(){

    }

}
