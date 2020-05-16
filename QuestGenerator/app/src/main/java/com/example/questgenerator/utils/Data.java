package com.example.questgenerator.utils;

import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;

import java.util.ArrayList;
import java.util.List;

public class Data {

    List<Enemy> enemies = new ArrayList<>();
    List<NPC> npcs = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    List<Item> weapons = new ArrayList<>();
    List<Item> armors = new ArrayList<>();
    List<Item> potions = new ArrayList<>();
    List<Item> scrolls = new ArrayList<>();
    List<Item> wands = new ArrayList<>();
    List<Item> rings = new ArrayList<>();
    List<Item> seeds = new ArrayList<>();
    List<Item> food = new ArrayList<>();
    List<Item> keys = new ArrayList<>();
    List<Item> bags = new ArrayList<>();
    List<Item> otherItems = new ArrayList<>();
    List<Location> locations = new ArrayList<>();

    /* Locations */
    Location sewers = new Location("Sewers");
    Location caves = new Location("Caves");
    Location dwarvenCity = new Location("Dwarven City");
    Location demonHalls = new Location("Demon Halls");
    Location town = new Location("Town");

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
    Item shortSword = new Item("Short Sword");
    Item dagger = new Item("Dagger");
    Item knuckleBuster = new Item("KnuckleBuster");
    Item quarterstaff = new Item("Quarterstaff");
    Item spear = new Item("Spear");
    Item mace = new Item("Mace");
    Item sword = new Item("Sword");
    Item longsword = new Item("Longsword");
    Item battleAxe = new Item("Battle Axe");
    Item warHammer = new Item("War Hammer");
    Item glaive = new Item("Glaive");
    Item pickAxe = new Item("Pickaxe");
    Item dart = new Item("Dart");
    Item shuriken = new Item("Shuriken");
    Item javelin = new Item("Javelin");
    Item tomohawk = new Item("Tomohawk");
    Item boomerang = new Item("Boomerang");
    Item bomb = new Item("Bomb");

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
    Item clothArmor = new Item("Cloth Armor");
    Item leatherArmor = new Item("Leather Armor");
    Item mailArmor = new Item("Mail Armor");
    Item scaleArmor = new Item("Scale Armor");
    Item plateArmor = new Item("Plate Armor");

    public List<Item> getArmors() {
        armors.add(clothArmor);
        armors.add(leatherArmor);
        armors.add(mailArmor);
        armors.add(scaleArmor);
        armors.add(plateArmor);
        return armors;
    }

    // Potions
    Item potionOfHealing = new Item("Potion of Healing");
    Item potionOfExperience = new Item("Potion of Experience");
    Item potionOfToxicGas = new Item("Potion of Toxic Gas");
    Item potionOfParalyticGas = new Item("Potion of Paralytic Gas");
    Item potionOfLiquidFlame = new Item("Potion of Liquid Flame");
    Item potionOfLevitation = new Item("Potion of Levitation");
    Item potionOfStrength = new Item("Potion of Strength");
    Item potionOfMindVision = new Item("Potion of Mind Vision");
    Item potionOfPurification = new Item("Potion of Purification");
    Item potionOfInvisibility = new Item("Potion of Invisibility");
    Item potionOfMight = new Item("Potion of Might");
    Item potionOfFrost = new Item("Potion of Frost");

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
    Item scrollOfIdentity = new Item("Scroll of Identity");
    Item scrollOfTeleportation = new Item("Scroll of Teleportation");
    Item scrollOfRemoveCurse = new Item("Scroll of Remove Curse");
    Item scrollOfRecharging = new Item("Scroll of Recharging");
    Item scrollOfMagicMapping = new Item("Scroll of Magic Mapping");
    Item scrollOfChallenge = new Item("Scroll of Challenge");
    Item scrollOfTerror = new Item("Scroll of Terror");
    Item scrollOfLullaby = new Item("Scroll of Lullaby");
    Item scrollOfPsyonicBlast = new Item("Scroll of Psyonic Blast");
    Item scrollOfMirrorImage = new Item("Scroll of Mirror Image");
    Item scrollOfUpgrade = new Item("Scroll of Upgrade");
    Item scrollOfEnchantment = new Item("Scroll of Enchantment");

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
    Item wandOfAvalanche = new Item("Wand of Avalanche");
    Item wandOfDisintegration = new Item("Wand of Disintegration");
    Item wandOfMagicMissile = new Item("Wand of Magic Missile");
    Item wandOfFirebolt = new Item("Wand of Firebolt");
    Item wandOfLightning = new Item("Wand of Lightning");
    Item wandOfPoison = new Item("Wand of Poison");
    Item wandOfAmok = new Item("Wand of Amok");
    Item wandOfBlink = new Item("Wand of Blink");
    Item wandOfFlock = new Item("Wand of Flock");
    Item wandOfRegrowth = new Item("Wand of Regrowth");
    Item wandOfSlowness = new Item("Wand of Slowness");
    Item wandOfTeleportation = new Item("Wand of Teleportation");
    Item wandOfReach = new Item("Wand of Reach");

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
    Item ringOfAccuracy = new Item("Ring of Accuracy");
    Item ringOfDetection = new Item("Ring of Detection");
    Item ringOfElements = new Item("Ring of Elements");
    Item ringOfEvasion = new Item("Ring of Evasion");
    Item ringOfHaggler = new Item("Ring of Haggler");
    Item ringOfHaste = new Item("Ring of Haste");
    Item ringOfHerbalism = new Item("Ring of Herbalism");
    Item ringOfMending = new Item("Ring of Mending");
    Item ringOfPower = new Item("Ring of Power");
    Item ringOfSatiety = new Item("Ring of Satiety");
    Item ringOfShadows = new Item("Ring of Shadows");
    Item ringOfThorns = new Item("Ring of Thorns");
    Item ringOfCleansing = new Item("Ring of Cleansing");
    Item ringOfEnergy = new Item("Ring of Energy");
    Item ringOfResistance = new Item("Ring of Resistance");

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
    Item seedOfFirebloom = new Item("Seed of Firebloom");
    Item seedOfIcecap = new Item("Seed of Icecap");
    Item seedOfSorrowmoss = new Item("Seed of Sorrowmoss");
    Item seedOfDreamweed = new Item("Seed of Dreamweed");
    Item seedOfSungrass = new Item("Seed of Sungrass");
    Item seedOfEarthroot = new Item("Seed of Earthroot");
    Item seedOfFadeleaf = new Item("Seed of Fadeleaf");
    Item seedOfRotberry = new Item("Seed of Rotberry");

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
    Item rationOfFood = new Item("Ration of Food");
    Item pasty = new Item("Pasty");
    Item overpricedFoodRation = new Item("Overpriced Food");
    Item mysteryMeat = new Item("Mystery Meat");
    Item chargrilledMeat = new Item("Char grilled Meat");
    Item frozenCarpaccio = new Item("Frozen Carpaccio");

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
    Item ironKey = new Item("Iron key");
    Item goldenKey = new Item("Golden key");
    Item skeletonKey = new Item("Skeleton key");

    public List<Item> getKeys() {
        keys.add(ironKey);
        keys.add(goldenKey);
        keys.add(skeletonKey);
        return keys;
    }

    // Bags
    Item seedPouch = new Item("Seed pouch");
    Item scrollHolder = new Item("Scroll holder");
    Item wandHolster = new Item("Wand holster");
    Item keyRing = new Item("Key ring");

    public List<Item> getBags() {
        bags.add(seedPouch);
        bags.add(scrollHolder);
        bags.add(wandHolster);
        bags.add(keyRing);
        return bags;
    }

    // Other
    Item dewdrop = new Item("Dewdrop");
    Item driedRose = new Item("Dried rose");
    Item giantRat = new Item("Giant rat");
    Item skull = new Item("Skull");
    Item corpseDust = new Item("Corpse dust");
    Item phantomFish = new Item("Phantom fish");
    Item darkGold = new Item("Dark gold");
    Item ore = new Item("Ore");
    Item dwarfToken = new Item("Dwarf token");
    Item gold = new Item("Gold");
    Item dewVial = new Item("Dew vial");
    Item honeypot = new Item("Honeypot");
    Item weightstone = new Item("Weightstone");
    Item ankh = new Item("Ankh");
    Item torch = new Item("Torch");
    Item lloydsBeacon = new Item("Lloyd's Beacon");
    Item tomeOfMastery = new Item("Tome of Mastery");
    Item armorKit = new Item("Armor kit");
    Item amuletOfYendor = new Item("Amulot of Yendor");

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
    NPC townGaurd = new NPC("Town Gaurd", town);
    NPC shopkeeper = new NPC("Shopkeeper", town);
    NPC townsfolk = new NPC("Townsfolk", town);
    NPC plagueDoctor = new NPC("Plague Doctor", town);
    NPC priest = new NPC("Priest", town);
    NPC employee = new NPC("Employee", town);
    NPC blackCat = new NPC("Black Cat", town);
    NPC fortuneTeller = new NPC("Fortune Teller", town);
    NPC librarian = new NPC("Librarian", town);

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
    Enemy rat = new Enemy("Rat", sewers);
    Enemy gnollScout = new Enemy("Gnoll Scout", sewers);
    Enemy crab = new Enemy("Crab", sewers);
    Enemy skeleton = new Enemy("Skeleton", sewers);
    Enemy crazyThief = new Enemy("Crazy Thief", sewers);
    Enemy swarmOfFlies = new Enemy("Swarm of Flies", sewers);
    Enemy gnollShaman = new Enemy("Gnoll Shaman", sewers);
    Enemy vampireBat = new Enemy("Vampire Bat", caves);
    Enemy gnollBrute = new Enemy("Gnoll Brute", caves);
    Enemy caveSpider = new Enemy("Cave Spider", caves);
    Enemy fireElemental = new Enemy("Fire Elemental", dwarvenCity);
    Enemy dwarfWarlock = new Enemy("Dwarf Warlock", dwarvenCity);
    Enemy dwarfMonk = new Enemy("Dwarf Monk", dwarvenCity);
    Enemy golem = new Enemy("Golem", dwarvenCity);
    Enemy succubus = new Enemy("Succubus", demonHalls);
    Enemy evilEye = new Enemy("Evil Eye", demonHalls);
    Enemy scorpion = new Enemy("Scorpion", demonHalls);
    Enemy acidScorpion = new Enemy("Acid Scorpion", demonHalls);
    Enemy wraith = new Enemy("Wraith", "special");
    Enemy animatedStatue = new Enemy("Animated Statue", "special");
    Enemy giantPiranha = new Enemy("Giant Piranha", "special");
    Enemy fetidRat = new Enemy("Fetid Rat", "special");
    Enemy curseWraith = new Enemy("Cursed Wraith", "special");
    Enemy mimic = new Enemy("Mimic", "special");
    Enemy undeadDwarf = new Enemy("Undead Dwarf", "special");
    Enemy rottingFist = new Enemy("Rotting Fist", "special");
    Enemy burningFist = new Enemy("Burning Fist", "special");
    Enemy goo = new Enemy("Goo", "boss");
    Enemy tengu = new Enemy("Tengu", "boss");
    Enemy tank = new Enemy("DM-300", "boss");
    Enemy kingOfDwarf = new Enemy("Kinf of Dwarf", "boss");
    Enemy eyeMonster = new Enemy("Yog-Dzewa", "boss");

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
        /* These enemies don't have locations */
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
