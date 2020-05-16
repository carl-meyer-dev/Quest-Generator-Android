package com.example.questgenerator.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Item extends RealmObject {
    /*  Note that this Item is an abstract representation of an item in the game only containing
        the item's name. The game will look for an item in the game world with this item's name
        in order to find it and use it. */

    @PrimaryKey
    @Required
    // Name of the Item.
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
