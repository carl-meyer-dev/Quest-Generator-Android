package com.example.questgenerator.utils;

import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;

import java.util.List;

public class Data {

    List<Enemy> enemies;
    List<NPC> npcs;
    List<Item> items;
    List<Location> locations;

    /* Locations */
    Location sewers = new Location("Sewers");
    Location caves = new Location("Caves");
    Location dwarvenCity = new Location("Dwarven City");
    Location demonHalls = new Location("Demon Halls");

    /* Items */

    // Weapons
    Item shortSword = new Item("Short Sword");
    Item dagger = new Item("Dagger");
    Item knuckleBuster = new Item("KnuckleBuster");
    Item Quarterstaff = new Item("Quarterstaff");
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

    // Armors
    Item clothArmor = new Item("Cloth Armor");
    Item leatherArmor = new Item("Leather Armor");
    Item mailArmor = new Item("Mail Armor");
    Item scaleArmor = new Item("Scale Armor");
    Item plateArmor = new Item("Plate Armor");

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

    // Seeds
    Item seedOfFirebloom = new Item("Seed of Firebloom");
    Item seedOfIcecap = new Item("Seed of Icecap");
    Item seedOfSorrowmoss = new Item("Seed of Sorrowmoss");
    Item seedOfDreamweed = new Item("Seed of Dreamweed");
    Item seedOfSungrass = new Item("Seed of Sungrass");
    Item seedOfEarthroot = new Item("Seed of Earthroot");
    Item seedOfFadeleaf = new Item("Seed of Fadeleaf");
    Item seedOfRotberry = new Item("Seed of Rotberry");

    // Food
    Item rationOfFood = new Item("Ration of Food");
    Item pasty = new Item("Pasty");
    Item overpricedFoodRation = new Item("Overpriced Food");
    Item mysteryMeat = new Item("Mystery Meat");
    Item chargrilledMeat = new Item("Char grilled Meat");
    Item frozenCarpaccio = new Item("Frozen Carpaccio");

    // Keys
    Item ironKey = new Item("Iron key");
    Item goldenKey = new Item("Golden key");
    Item skeletonKey = new Item("Skeleton key");
    // Bags
    Item seedPouch = new Item("Seed pouch");
    Item scrollHolder = new Item("Scroll holder");
    Item wandHolster = new Item("Wand holster");
    Item keyRing = new Item("Key ring");
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

    /* Enemies */
    Enemy rat = new Enemy("Rat", )

}
