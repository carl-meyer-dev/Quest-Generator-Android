package com.carlmeyer.questgeneratordemo.questgenerator.models;

import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * This is the database representation of the Action class.
 * We need to use this class as a facade in order for us to have a list of action in the database
 * so we can build story fragments. The DBActions used to build story fragments will then map back to
 * the Action class used in the quest generator.
 */
public class DBAction extends RealmObject {

    @PrimaryKey
    private int id;

    private String action;

    private RealmList<String> configs;
    
    private String config;

    public DBAction(){

    }

    public DBAction(int id, String action, String[] configs){
        this.id = id;
        this.action = action;
        this.configs = getRealmListOfConfigs(configs);
    }

    public DBAction(int id, String action, RealmList<String> configs, String config){
        this.id = id;
        this.action = action;
        this.configs = configs;
        this.config = config;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<String> getConfigs() {
        return configs;
    }

    public void setConfigs(RealmList<String> configs) {
        this.configs = configs;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * A method to convert the String[] of configurations in to a RealmList
     * @param configs - list of configurations (this relates to the Action's constructors)
     * @return a realmList of configurations
     */
    private RealmList<String> getRealmListOfConfigs(String[] configs){
        RealmList<String> realmListOfConfigs = new RealmList<>();

        realmListOfConfigs.addAll(Arrays.asList(configs));

        return realmListOfConfigs;
    }

    private String[] getStringArrayOfConfigs(RealmList<String> configs){
        String[] stringArrayOfConfigs = new String[configs.size()];
        for (int i = 0; i < configs.size(); i++) {
            stringArrayOfConfigs[i] = configs.get(i);
        }
        return stringArrayOfConfigs;
    }
}
