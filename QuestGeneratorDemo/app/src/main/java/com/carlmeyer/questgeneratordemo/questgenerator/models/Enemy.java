package com.carlmeyer.questgeneratordemo.questgenerator.models;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Enemy extends RealmObject {

    /* The @PrimaryKey Annotation indicates the Primary Key field for this object and automatically
       indexes the field so we can run faster queries on that field */
    @PrimaryKey
    private int id;
    // Name of the Enemy.
    private String name;
    // Location of the Enemy. One to one relationship in realm db.

    private Location location;
    // A list of Items that the enemy has on them. This is a one to many relationship in Realm
    private RealmList<Item> loot; //Note we have to use RealmList in order to map relationship
    // A flag to indicate whether or not the enemy is a boss
    private boolean boss = false;
    // A flag to indicate whether or not the enemy is a special enemy
    private boolean special = false;

    //TODO: Might not need constructors anymore since we will use Realm

    public Enemy(){

    }

    public Enemy(String name, Location location, RealmList<Item> loot){
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RealmList<Item> getLoot() {
        return loot;
    }

    public void setLoot(RealmList<Item> loot) {
        this.loot = loot;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }
}
