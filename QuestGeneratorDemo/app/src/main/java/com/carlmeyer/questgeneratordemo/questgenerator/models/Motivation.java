package com.carlmeyer.questgeneratordemo.questgenerator.models;

import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Motivation extends RealmObject {



    @PrimaryKey
    private int id;

    private String motivation;

    private RealmList<StoryFragment> storyFragments = new RealmList<>();

    public Motivation(){}

    public Motivation(int id, String motivation){
        this.id = id;
        this.motivation = motivation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public void addStoryFragment(StoryFragment storyFragment){
        this.storyFragments.add(storyFragment);
    }

    public RealmList<StoryFragment> getStoryFragments() {
        return storyFragments;
    }

    public void setStoryFragments(RealmList<StoryFragment> storyFragments) {
        this.storyFragments = storyFragments;
    }
}
