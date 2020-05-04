package com.example.questgenerator.models;

import java.util.List;

public class Enemy {

    public String name;
    public Location location;
    public Item[] loot;

    public Enemy(String name, Location location, Item[] loot){
        this.name = name;
        this.location = location;
        this.loot = loot;
    }

}
