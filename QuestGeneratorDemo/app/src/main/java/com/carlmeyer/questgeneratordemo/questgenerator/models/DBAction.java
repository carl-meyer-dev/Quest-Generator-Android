package com.carlmeyer.questgeneratordemo.questgenerator.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DBAction extends RealmObject {

    @PrimaryKey
    private int id;

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
