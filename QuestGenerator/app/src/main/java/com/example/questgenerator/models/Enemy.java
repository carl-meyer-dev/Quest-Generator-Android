package com.example.questgenerator.models;

import com.example.questgenerator.generator.QuestGenerator;

import java.util.List;

public class Enemy {

    public String name;
    public Location location;
    public Item[] loot;
    public boolean boss = false;
    public boolean special = false;

    public Enemy(String name, Location location, Item[] loot){
        this.name = name;
        this.location = location;
        this.loot = loot;
    }

    public Enemy(String name, Location location){
        this.name = name;
        this.location = location;
    }

    public Enemy(String name, String type){
        this.name = name;
        if(type.equals("special")){
            this.special = true;
        }else if(type.equals("boss")){
            this.special = true;
        }
    }

}
