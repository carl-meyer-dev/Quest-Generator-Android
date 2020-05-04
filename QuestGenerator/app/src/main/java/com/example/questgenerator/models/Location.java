package com.example.questgenerator.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Location {

    public String name;
    public List<Enemy> enemies = new ArrayList<>();
    public List<NPC> npcs = new ArrayList<>();

    public Location(String name) {
        this.name = name;
    }

    public Location(String name, @Nullable List<Enemy> enemies, @Nullable List<NPC> npcs) {

        this.name = name;

        if (npcs != null) {
            this.npcs = npcs;
        }

        if (enemies != null) {
            this.enemies = enemies;
        }
    }

}
