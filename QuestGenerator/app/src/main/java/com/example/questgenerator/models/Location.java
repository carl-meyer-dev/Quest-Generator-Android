package com.example.questgenerator.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Location extends RealmObject {

    @PrimaryKey
    @Required
    // Name of the location. Primary key and may not be null
    private String name;
    // List of enemies present in location. One to many relationship in realm db
    private RealmList<Enemy> enemies;
    // List of NPCs in location. One to many relationship in realm db
    private RealmList<NPC> npcs;

    public Location(String name) {
        this.name = name;
    }

    public Location(String name, @Nullable RealmList<Enemy> enemies, @Nullable RealmList<NPC> npcs) {

        this.name = name;

        if (npcs != null) {
            this.npcs = npcs;
        }

        if (enemies != null) {
            this.enemies = enemies;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(RealmList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public RealmList<NPC> getNpcs() {
        return npcs;
    }

    public void setNpcs(RealmList<NPC> npcs) {
        this.npcs = npcs;
    }
}
