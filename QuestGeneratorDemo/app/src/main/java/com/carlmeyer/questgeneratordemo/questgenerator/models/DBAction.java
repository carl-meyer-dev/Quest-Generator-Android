package com.carlmeyer.questgeneratordemo.questgenerator.models;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;

public class DBAction implements RealmModel {

    @PrimaryKey
    private String action;

}
