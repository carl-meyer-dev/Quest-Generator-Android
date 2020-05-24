package com.carlmeyer.questgeneratordemo.questgenerator.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Item extends RealmObject {
    /*  Note that this Item is an abstract representation of an item in the game only containing
        the item's name. The game will look for an item in the game world with this item's name
        in order to find it and use it. */

    @PrimaryKey
    private int id;
    // Name of the item.
    private String name;
    // Category of item
    private String category;

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, String category) {
        this.name = name;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
