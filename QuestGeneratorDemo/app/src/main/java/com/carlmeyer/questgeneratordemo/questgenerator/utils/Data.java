package com.carlmeyer.questgeneratordemo.questgenerator.utils;


import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;

import java.util.ArrayList;
import java.util.List;

public class Data {

    /*
    The Data class represents all the Locations, Enemies, NPCs and Items that is present
    in Pixel Dungeon. I wanted to use this data set for my Quest Generator Demo to get an accurate
    representation of the types of quests that can be generated for Pixel Dungeon.
     */

    /*
    I made this Data class in my initial version of my Quest Generator Demo App and hardcoded the data
    since I did not initially think I would need to use a DB. But since I have decided to expand the
    demo app further and include a DB to perform CRUD operations, I will use this data class in order
    to load initial data into the application when the DB is created for the first time or reset.

    I could have alternatively used a JSON file and import the data into the DB using GSON but since
    I had this Data Class that I have made already, I decided to use this Data Class instead in order
    to save time.
     */

    private List<Enemy> enemies = new ArrayList<>();
    private List<NPC> npcs = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Item> weapons = new ArrayList<>();
    private List<Item> armors = new ArrayList<>();
    private List<Item> potions = new ArrayList<>();
    private List<Item> scrolls = new ArrayList<>();
    private List<Item> wands = new ArrayList<>();
    private List<Item> rings = new ArrayList<>();
    private List<Item> seeds = new ArrayList<>();
    private List<Item> food = new ArrayList<>();
    private List<Item> keys = new ArrayList<>();
    private List<Item> bags = new ArrayList<>();
    private List<Item> otherItems = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();

    /* Locations */
    private Location sewers = new Location("Sewers");
    private Location caves = new Location("Caves");
    private Location dwarvenCity = new Location("Dwarven City");
    private Location demonHalls = new Location("Demon Halls");
    private Location town = new Location("Town");

    public List<Location> getLocations() {
        locations.add(sewers);
        locations.add(caves);
        locations.add(dwarvenCity);
        locations.add(demonHalls);
        locations.add(town);
        return locations;
    }

    /* Items */

    // Weapons
    private Item shortSword = new Item("Short Sword");
    private Item dagger = new Item("Dagger");
    private Item knuckleBuster = new Item("KnuckleBuster");
    private Item quarterstaff = new Item("Quarterstaff");
    private Item spear = new Item("Spear");
    private Item mace = new Item("Mace");
    private Item sword = new Item("Sword");
    private Item longsword = new Item("Longsword");
    private Item battleAxe = new Item("Battle Axe");
    private Item warHammer = new Item("War Hammer");
    private Item glaive = new Item("Glaive");
    private Item pickAxe = new Item("Pickaxe");
    private Item dart = new Item("Dart");
    private Item shuriken = new Item("Shuriken");
    private Item javelin = new Item("Javelin");
    private Item tomohawk = new Item("Tomohawk");
    private Item boomerang = new Item("Boomerang");
    private Item bomb = new Item("Bomb");

    public List<Item> getWeapons() {
        weapons.add(shortSword);
        weapons.add(dagger);
        weapons.add(knuckleBuster);
        weapons.add(quarterstaff);
        weapons.add(spear);
        weapons.add(mace);
        weapons.add(sword);
        weapons.add(longsword);
        weapons.add(battleAxe);
        weapons.add(warHammer);
        weapons.add(glaive);
        weapons.add(pickAxe);
        weapons.add(dart);
        weapons.add(shuriken);
        weapons.add(javelin);
        weapons.add(tomohawk);
        weapons.add(boomerang);
        weapons.add(bomb);
        return weapons;
    }

    // Armors
    private Item clothArmor = new Item("Cloth Armor");
    private Item leatherArmor = new Item("Leather Armor");
    private Item mailArmor = new Item("Mail Armor");
    private Item scaleArmor = new Item("Scale Armor");
    private Item plateArmor = new Item("Plate Armor");

    public List<Item> getArmors() {
        armors.add(clothArmor);
        armors.add(leatherArmor);
        armors.add(mailArmor);
        armors.add(scaleArmor);
        armors.add(plateArmor);
        return armors;
    }

    // Potions
    private Item potionOfHealing = new Item("Potion of Healing");
    private Item potionOfExperience = new Item("Potion of Experience");
    private Item potionOfToxicGas = new Item("Potion of Toxic Gas");
    private Item potionOfParalyticGas = new Item("Potion of Paralytic Gas");
    private Item potionOfLiquidFlame = new Item("Potion of Liquid Flame");
    private Item potionOfLevitation = new Item("Potion of Levitation");
    private Item potionOfStrength = new Item("Potion of Strength");
    private Item potionOfMindVision = new Item("Potion of Mind Vision");
    private Item potionOfPurification = new Item("Potion of Purification");
    private Item potionOfInvisibility = new Item("Potion of Invisibility");
    private Item potionOfMight = new Item("Potion of Might");
    private Item potionOfFrost = new Item("Potion of Frost");

    public List<Item> getPotions() {
        potions.add(potionOfHealing);
        potions.add(potionOfExperience);
        potions.add(potionOfToxicGas);
        potions.add(potionOfParalyticGas);
        potions.add(potionOfLiquidFlame);
        potions.add(potionOfLevitation);
        potions.add(potionOfStrength);
        potions.add(potionOfMindVision);
        potions.add(potionOfPurification);
        potions.add(potionOfInvisibility);
        potions.add(potionOfMight);
        potions.add(potionOfFrost);
        return potions;
    }

    // Scrolls
    private Item scrollOfIdentity = new Item("Scroll of Identity");
    private Item scrollOfTeleportation = new Item("Scroll of Teleportation");
    private Item scrollOfRemoveCurse = new Item("Scroll of Remove Curse");
    private Item scrollOfRecharging = new Item("Scroll of Recharging");
    private Item scrollOfMagicMapping = new Item("Scroll of Magic Mapping");
    private Item scrollOfChallenge = new Item("Scroll of Challenge");
    private Item scrollOfTerror = new Item("Scroll of Terror");
    private Item scrollOfLullaby = new Item("Scroll of Lullaby");
    private Item scrollOfPsyonicBlast = new Item("Scroll of Psyonic Blast");
    private Item scrollOfMirrorImage = new Item("Scroll of Mirror Image");
    private Item scrollOfUpgrade = new Item("Scroll of Upgrade");
    private Item scrollOfEnchantment = new Item("Scroll of Enchantment");

    public List<Item> getScrolls() {
        scrolls.add(scrollOfIdentity);
        scrolls.add(scrollOfTeleportation);
        scrolls.add(scrollOfRemoveCurse);
        scrolls.add(scrollOfRecharging);
        scrolls.add(scrollOfMagicMapping);
        scrolls.add(scrollOfChallenge);
        scrolls.add(scrollOfTerror);
        scrolls.add(scrollOfLullaby);
        scrolls.add(scrollOfPsyonicBlast);
        scrolls.add(scrollOfMirrorImage);
        scrolls.add(scrollOfUpgrade);
        scrolls.add(scrollOfEnchantment);
        return scrolls;
    }

    // Wands
    private Item wandOfAvalanche = new Item("Wand of Avalanche");
    private Item wandOfDisintegration = new Item("Wand of Disintegration");
    private Item wandOfMagicMissile = new Item("Wand of Magic Missile");
    private Item wandOfFirebolt = new Item("Wand of Firebolt");
    private Item wandOfLightning = new Item("Wand of Lightning");
    private Item wandOfPoison = new Item("Wand of Poison");
    private Item wandOfAmok = new Item("Wand of Amok");
    private Item wandOfBlink = new Item("Wand of Blink");
    private Item wandOfFlock = new Item("Wand of Flock");
    private Item wandOfRegrowth = new Item("Wand of Regrowth");
    private Item wandOfSlowness = new Item("Wand of Slowness");
    private Item wandOfTeleportation = new Item("Wand of Teleportation");
    private Item wandOfReach = new Item("Wand of Reach");

    public List<Item> getWands() {
        wands.add(wandOfAvalanche);
        wands.add(wandOfDisintegration);
        wands.add(wandOfMagicMissile);
        wands.add(wandOfFirebolt);
        wands.add(wandOfLightning);
        wands.add(wandOfPoison);
        wands.add(wandOfAmok);
        wands.add(wandOfBlink);
        wands.add(wandOfFlock);
        wands.add(wandOfRegrowth);
        wands.add(wandOfSlowness);
        wands.add(wandOfTeleportation);
        wands.add(wandOfReach);
        return wands;
    }

    // Rings
    private Item ringOfAccuracy = new Item("Ring of Accuracy");
    private Item ringOfDetection = new Item("Ring of Detection");
    private Item ringOfElements = new Item("Ring of Elements");
    private Item ringOfEvasion = new Item("Ring of Evasion");
    private Item ringOfHaggler = new Item("Ring of Haggler");
    private Item ringOfHaste = new Item("Ring of Haste");
    private Item ringOfHerbalism = new Item("Ring of Herbalism");
    private Item ringOfMending = new Item("Ring of Mending");
    private Item ringOfPower = new Item("Ring of Power");
    private Item ringOfSatiety = new Item("Ring of Satiety");
    private Item ringOfShadows = new Item("Ring of Shadows");
    private Item ringOfThorns = new Item("Ring of Thorns");
    private Item ringOfCleansing = new Item("Ring of Cleansing");
    private Item ringOfEnergy = new Item("Ring of Energy");
    private Item ringOfResistance = new Item("Ring of Resistance");

    public List<Item> getRings() {
        rings.add(ringOfAccuracy);
        rings.add(ringOfDetection);
        rings.add(ringOfElements);
        rings.add(ringOfEvasion);
        rings.add(ringOfHaggler);
        rings.add(ringOfHaste);
        rings.add(ringOfHerbalism);
        rings.add(ringOfMending);
        rings.add(ringOfPower);
        rings.add(ringOfSatiety);
        rings.add(ringOfShadows);
        rings.add(ringOfThorns);
        rings.add(ringOfCleansing);
        rings.add(ringOfEnergy);
        rings.add(ringOfResistance);
        return rings;
    }

    // Seeds
    private Item seedOfFirebloom = new Item("Seed of Firebloom");
    private Item seedOfIcecap = new Item("Seed of Icecap");
    private Item seedOfSorrowmoss = new Item("Seed of Sorrowmoss");
    private Item seedOfDreamweed = new Item("Seed of Dreamweed");
    private Item seedOfSungrass = new Item("Seed of Sungrass");
    private Item seedOfEarthroot = new Item("Seed of Earthroot");
    private Item seedOfFadeleaf = new Item("Seed of Fadeleaf");
    private Item seedOfRotberry = new Item("Seed of Rotberry");

    public List<Item> getSeeds() {
        seeds.add(seedOfFirebloom);
        seeds.add(seedOfIcecap);
        seeds.add(seedOfSorrowmoss);
        seeds.add(seedOfDreamweed);
        seeds.add(seedOfSungrass);
        seeds.add(seedOfEarthroot);
        seeds.add(seedOfFadeleaf);
        seeds.add(seedOfRotberry);
        return seeds;
    }

    // Food
    private Item rationOfFood = new Item("Ration of Food");
    private Item pasty = new Item("Pasty");
    private Item overpricedFoodRation = new Item("Overpriced Food");
    private Item mysteryMeat = new Item("Mystery Meat");
    private Item chargrilledMeat = new Item("Char grilled Meat");
    private Item frozenCarpaccio = new Item("Frozen Carpaccio");

    public List<Item> getFood() {
        food.add(rationOfFood);
        food.add(pasty);
        food.add(overpricedFoodRation);
        food.add(mysteryMeat);
        food.add(chargrilledMeat);
        food.add(frozenCarpaccio);
        return food;
    }

    // Keys
    private Item ironKey = new Item("Iron key");
    private Item goldenKey = new Item("Golden key");
    private Item skeletonKey = new Item("Skeleton key");

    public List<Item> getKeys() {
        keys.add(ironKey);
        keys.add(goldenKey);
        keys.add(skeletonKey);
        return keys;
    }

    // Bags
    private Item seedPouch = new Item("Seed pouch");
    private Item scrollHolder = new Item("Scroll holder");
    private Item wandHolster = new Item("Wand holster");
    private Item keyRing = new Item("Key ring");

    public List<Item> getBags() {
        bags.add(seedPouch);
        bags.add(scrollHolder);
        bags.add(wandHolster);
        bags.add(keyRing);
        return bags;
    }

    // Other
    private Item dewdrop = new Item("Dewdrop");
    private Item driedRose = new Item("Dried rose");
    private Item giantRat = new Item("Giant rat");
    private Item skull = new Item("Skull");
    private Item corpseDust = new Item("Corpse dust");
    private Item phantomFish = new Item("Phantom fish");
    private Item darkGold = new Item("Dark gold");
    private Item ore = new Item("Ore");
    private Item dwarfToken = new Item("Dwarf token");
    private Item gold = new Item("Gold");
    private Item dewVial = new Item("Dew vial");
    private Item honeypot = new Item("Honeypot");
    private Item weightstone = new Item("Weightstone");
    private Item ankh = new Item("Ankh");
    private Item torch = new Item("Torch");
    private Item lloydsBeacon = new Item("Lloyd's Beacon");
    private Item tomeOfMastery = new Item("Tome of Mastery");
    private Item armorKit = new Item("Armor kit");
    private Item amuletOfYendor = new Item("Amulot of Yendor");

    public List<Item> getOtherItems() {
        otherItems.add(dewdrop);
        otherItems.add(driedRose);
        otherItems.add(giantRat);
        otherItems.add(skull);
        otherItems.add(corpseDust);
        otherItems.add(phantomFish);
        otherItems.add(darkGold);
        otherItems.add(ore);
        otherItems.add(dwarfToken);
        otherItems.add(gold);
        otherItems.add(dewVial);
        otherItems.add(honeypot);
        otherItems.add(weightstone);
        otherItems.add(ankh);
        otherItems.add(torch);
        otherItems.add(lloydsBeacon);
        otherItems.add(tomeOfMastery);
        otherItems.add(armorKit);
        otherItems.add(amuletOfYendor);
        return otherItems;
    }

    /* NPCs */
    private NPC townGaurd = new NPC("Town Gaurd", town);
    private NPC shopkeeper = new NPC("Shopkeeper", town);
    private NPC townsfolk = new NPC("Townsfolk", town);
    private NPC plagueDoctor = new NPC("Plague Doctor", town);
    private NPC priest = new NPC("Priest", town);
    private NPC employee = new NPC("Employee", town);
    private NPC blackCat = new NPC("Black Cat", town);
    private NPC fortuneTeller = new NPC("Fortune Teller", town);
    private NPC librarian = new NPC("Librarian", town);

    /**
     * Get all the npcs in Data
     *
     * @return npcs
     */
    public List<NPC> getNpcs() {
        npcs.add(townGaurd);
        npcs.add(shopkeeper);
        npcs.add(townsfolk);
        npcs.add(plagueDoctor);
        npcs.add(priest);
        npcs.add(employee);
        npcs.add(blackCat);
        npcs.add(fortuneTeller);
        npcs.add(librarian);
        return npcs;
    }

    /* Enemies */
    private Enemy rat = new Enemy("Rat", sewers);
    private Enemy gnollScout = new Enemy("Gnoll Scout", sewers);
    private Enemy crab = new Enemy("Crab", sewers);
    private Enemy skeleton = new Enemy("Skeleton", sewers);
    private Enemy crazyThief = new Enemy("Crazy Thief", sewers);
    private Enemy swarmOfFlies = new Enemy("Swarm of Flies", sewers);
    private Enemy gnollShaman = new Enemy("Gnoll Shaman", sewers);
    private Enemy vampireBat = new Enemy("Vampire Bat", caves);
    private Enemy gnollBrute = new Enemy("Gnoll Brute", caves);
    private Enemy caveSpider = new Enemy("Cave Spider", caves);
    private Enemy fireElemental = new Enemy("Fire Elemental", dwarvenCity);
    private Enemy dwarfWarlock = new Enemy("Dwarf Warlock", dwarvenCity);
    private Enemy dwarfMonk = new Enemy("Dwarf Monk", dwarvenCity);
    private Enemy golem = new Enemy("Golem", dwarvenCity);
    private Enemy succubus = new Enemy("Succubus", demonHalls);
    private Enemy evilEye = new Enemy("Evil Eye", demonHalls);
    private Enemy scorpion = new Enemy("Scorpion", demonHalls);
    private Enemy acidScorpion = new Enemy("Acid Scorpion", demonHalls);
    private Enemy wraith = new Enemy("Wraith", "special");
    private Enemy animatedStatue = new Enemy("Animated Statue", "special");
    private Enemy giantPiranha = new Enemy("Giant Piranha", "special");
    private Enemy fetidRat = new Enemy("Fetid Rat", "special");
    private Enemy curseWraith = new Enemy("Cursed Wraith", "special");
    private Enemy mimic = new Enemy("Mimic", "special");
    private Enemy undeadDwarf = new Enemy("Undead Dwarf", "special");
    private Enemy rottingFist = new Enemy("Rotting Fist", "special");
    private Enemy burningFist = new Enemy("Burning Fist", "special");
    private Enemy goo = new Enemy("Goo", "boss");
    private Enemy tengu = new Enemy("Tengu", "boss");
    private Enemy tank = new Enemy("DM-300", "boss");
    private Enemy kingOfDwarf = new Enemy("Kinf of Dwarf", "boss");
    private Enemy eyeMonster = new Enemy("Yog-Dzewa", "boss");

    /**
     * Get all the enemies in Data
     *
     * @return enemies
     */
    public List<Enemy> getEnemies() {
        enemies.add(rat);
        enemies.add(gnollScout);
        enemies.add(crab);
        enemies.add(skeleton);
        enemies.add(crazyThief);
        enemies.add(swarmOfFlies);
        enemies.add(gnollShaman);
        enemies.add(vampireBat);
        enemies.add(gnollBrute);
        enemies.add(caveSpider);
        enemies.add(fireElemental);
        enemies.add(dwarfWarlock);
        enemies.add(dwarfMonk);
        enemies.add(golem);
        enemies.add(succubus);
        enemies.add(evilEye);
        enemies.add(scorpion);
        enemies.add(acidScorpion);
        /* These enemies don't have locations which will cause null exceptions. I'll keep them commented for now */
//        enemies.add(wraith);
//        enemies.add(animatedStatue);
//        enemies.add(giantPiranha);
//        enemies.add(fetidRat);
//        enemies.add(curseWraith);
//        enemies.add(mimic);
//        enemies.add(undeadDwarf);
//        enemies.add(rottingFist);
//        enemies.add(burningFist);
//        enemies.add(goo);
//        enemies.add(tengu);
//        enemies.add(tank);
//        enemies.add(kingOfDwarf);
//        enemies.add(eyeMonster);
        return enemies;
    }

    /**
     * Get all the items in Data
     *
     * @return items
     */
    public List<Item> getItems() {
        items.add(shortSword);
        items.add(dagger);
        items.add(knuckleBuster);
        items.add(quarterstaff);
        items.add(spear);
        items.add(mace);
        items.add(sword);
        items.add(longsword);
        items.add(battleAxe);
        items.add(warHammer);
        items.add(glaive);
        items.add(pickAxe);
        items.add(dart);
        items.add(shuriken);
        items.add(javelin);
        items.add(tomohawk);
        items.add(boomerang);
        items.add(bomb);
        items.add(clothArmor);
        items.add(leatherArmor);
        items.add(mailArmor);
        items.add(scaleArmor);
        items.add(plateArmor);
        items.add(potionOfHealing);
        items.add(potionOfExperience);
        items.add(potionOfToxicGas);
        items.add(potionOfParalyticGas);
        items.add(potionOfLiquidFlame);
        items.add(potionOfLevitation);
        items.add(potionOfStrength);
        items.add(potionOfMindVision);
        items.add(potionOfPurification);
        items.add(potionOfInvisibility);
        items.add(potionOfMight);
        items.add(potionOfFrost);
        items.add(scrollOfIdentity);
        items.add(scrollOfTeleportation);
        items.add(scrollOfRemoveCurse);
        items.add(scrollOfRecharging);
        items.add(scrollOfMagicMapping);
        items.add(scrollOfChallenge);
        items.add(scrollOfTerror);
        items.add(scrollOfLullaby);
        items.add(scrollOfPsyonicBlast);
        items.add(scrollOfMirrorImage);
        items.add(scrollOfUpgrade);
        items.add(scrollOfEnchantment);
        items.add(wandOfAvalanche);
        items.add(wandOfDisintegration);
        items.add(wandOfMagicMissile);
        items.add(wandOfFirebolt);
        items.add(wandOfLightning);
        items.add(wandOfPoison);
        items.add(wandOfAmok);
        items.add(wandOfBlink);
        items.add(wandOfFlock);
        items.add(wandOfRegrowth);
        items.add(wandOfSlowness);
        items.add(wandOfTeleportation);
        items.add(wandOfReach);
        items.add(ringOfAccuracy);
        items.add(ringOfDetection);
        items.add(ringOfElements);
        items.add(ringOfEvasion);
        items.add(ringOfHaggler);
        items.add(ringOfHaste);
        items.add(ringOfHerbalism);
        items.add(ringOfMending);
        items.add(ringOfPower);
        items.add(ringOfSatiety);
        items.add(ringOfShadows);
        items.add(ringOfThorns);
        items.add(ringOfCleansing);
        items.add(ringOfEnergy);
        items.add(ringOfResistance);
        items.add(seedOfFirebloom);
        items.add(seedOfIcecap);
        items.add(seedOfSorrowmoss);
        items.add(seedOfDreamweed);
        items.add(seedOfSungrass);
        items.add(seedOfEarthroot);
        items.add(seedOfFadeleaf);
        items.add(seedOfRotberry);
        items.add(rationOfFood);
        items.add(pasty);
        items.add(overpricedFoodRation);
        items.add(mysteryMeat);
        items.add(chargrilledMeat);
        items.add(frozenCarpaccio);
        items.add(ironKey);
        items.add(goldenKey);
        items.add(skeletonKey);
        items.add(seedPouch);
        items.add(scrollHolder);
        items.add(wandHolster);
        items.add(keyRing);
        items.add(dewdrop);
        items.add(driedRose);
        items.add(giantRat);
        items.add(skull);
        items.add(corpseDust);
        items.add(phantomFish);
        items.add(darkGold);
        items.add(ore);
        items.add(dwarfToken);
        items.add(gold);
        items.add(dewVial);
        items.add(honeypot);
        items.add(weightstone);
        items.add(ankh);
        items.add(torch);
        items.add(lloydsBeacon);
        items.add(tomeOfMastery);
        items.add(armorKit);
        items.add(amuletOfYendor);
        return items;
    }
}
