package com.example.pedometerpixeldungeon.mainsrc.items;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.Armor;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.ClothArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.LeatherArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.MailArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.PlateArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.ScaleArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.bags.Bag;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.Food;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.MysteryMeat;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.Pasty;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.Potion;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfExperience;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfFrost;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfHealing;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfInvisibility;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfLevitation;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfLiquidFlame;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfMight;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfMindVision;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfParalyticGas;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfPurity;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfStrength;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfToxicGas;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.Ring;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfAccuracy;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfDetection;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfElements;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfEvasion;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfHaggler;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfHaste;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfHerbalism;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfMending;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfPower;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfSatiety;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfShadows;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfThorns;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.Scroll;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfChallenge;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfEnchantment;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfIdentify;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfLullaby;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfMagicMapping;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfMirrorImage;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfPsionicBlast;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfRecharging;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfRemoveCurse;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfTeleportation;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfTerror;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfUpgrade;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.Wand;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfAmok;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfAvalanche;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfBlink;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfDisintegration;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfFirebolt;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfFlock;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfLightning;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfMagicMissile;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfPoison;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfReach;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfRegrowth;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfSlowness;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfTeleportation;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.Weapon;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.BattleAxe;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Dagger;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Glaive;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Knuckles;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Longsword;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Mace;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Quarterstaff;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.ShortSword;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Spear;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Sword;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.WarHammer;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.missiles.Boomerang;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.missiles.CurareDart;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.missiles.Dart;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.missiles.IncendiaryDart;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.missiles.Javelin;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.missiles.Shuriken;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.missiles.Tamahawk;
import com.example.pedometerpixeldungeon.mainsrc.plants.Dreamweed;
import com.example.pedometerpixeldungeon.mainsrc.plants.Earthroot;
import com.example.pedometerpixeldungeon.mainsrc.plants.Fadeleaf;
import com.example.pedometerpixeldungeon.mainsrc.plants.Firebloom;
import com.example.pedometerpixeldungeon.mainsrc.plants.Icecap;
import com.example.pedometerpixeldungeon.mainsrc.plants.Plant;
import com.example.pedometerpixeldungeon.mainsrc.plants.Rotberry;
import com.example.pedometerpixeldungeon.mainsrc.plants.Sorrowmoss;
import com.example.pedometerpixeldungeon.mainsrc.plants.Sungrass;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.HashMap;

public class Generator {

    public static enum Category {
        WEAPON	( 15,	Weapon.class ),
        ARMOR	( 10,	Armor.class ),
        POTION	( 50,	Potion.class ),
        SCROLL	( 40,	Scroll.class ),
        WAND	( 4,	Wand.class ),
        RING	( 2,	Ring.class ),
        SEED	( 5,	Plant.Seed.class ),
        FOOD	( 0,	Food.class ),
        GOLD	( 50,	Gold.class ),
        MISC	( 5,	Item.class );

        public Class<?>[] classes;
        public float[] probs;

        public float prob;
        public Class<? extends Item> superClass;

        private Category( float prob, Class<? extends Item> superClass ) {
            this.prob = prob;
            this.superClass = superClass;
        }

        public static int order( Item item ) {
            for (int i=0; i < values().length; i++) {
                if (values()[i].superClass.isInstance( item )) {
                    return i;
                }
            }

            return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
        }
    };

    private static HashMap<Category,Float> categoryProbs = new HashMap<Generator.Category, Float>();

    static {

        Category.GOLD.classes = new Class<?>[]{
                Gold.class };
        Category.GOLD.probs = new float[]{ 1 };

        Category.SCROLL.classes = new Class<?>[]{
                ScrollOfIdentify.class,
                ScrollOfTeleportation.class,
                ScrollOfRemoveCurse.class,
                ScrollOfRecharging.class,
                ScrollOfMagicMapping.class,
                ScrollOfChallenge.class,
                ScrollOfTerror.class,
                ScrollOfLullaby.class,
                ScrollOfPsionicBlast.class,
                ScrollOfMirrorImage.class,
                ScrollOfUpgrade.class,
                ScrollOfEnchantment.class };
        Category.SCROLL.probs = new float[]{ 30, 10, 15, 10, 15, 12, 8, 8, 4, 6, 0, 1 };

        Category.POTION.classes = new Class<?>[]{
                PotionOfHealing.class,
                PotionOfExperience.class,
                PotionOfToxicGas.class,
                PotionOfParalyticGas.class,
                PotionOfLiquidFlame.class,
                PotionOfLevitation.class,
                PotionOfStrength.class,
                PotionOfMindVision.class,
                PotionOfPurity.class,
                PotionOfInvisibility.class,
                PotionOfMight.class,
                PotionOfFrost.class };
        Category.POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };

        Category.WAND.classes = new Class<?>[]{
                WandOfTeleportation.class,
                WandOfSlowness.class,
                WandOfFirebolt.class,
                WandOfRegrowth.class,
                WandOfPoison.class,
                WandOfBlink.class,
                WandOfLightning.class,
                WandOfAmok.class,
                WandOfReach.class,
                WandOfFlock.class,
                WandOfMagicMissile.class,
                WandOfDisintegration.class,
                WandOfAvalanche.class };
        Category.WAND.probs = new float[]{ 10, 10, 15, 6, 10, 11, 15, 10, 6, 10, 0, 5, 5 };

        Category.WEAPON.classes = new Class<?>[]{
                Dagger.class,
                Knuckles.class,
                Quarterstaff.class,
                Spear.class,
                Mace.class,
                Sword.class,
                Longsword.class,
                BattleAxe.class,
                WarHammer.class,
                Glaive.class,
                ShortSword.class,
                Dart.class,
                Javelin.class,
                IncendiaryDart.class,
                CurareDart.class,
                Shuriken.class,
                Boomerang.class,
                Tamahawk.class };
        Category.WEAPON.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1 };

        Category.ARMOR.classes = new Class<?>[]{
                ClothArmor.class,
                LeatherArmor.class,
                MailArmor.class,
                ScaleArmor.class,
                PlateArmor.class
                };
        Category.ARMOR.probs = new float[]{ 1, 1, 1, 1, 1 };

        Category.FOOD.classes = new Class<?>[]{
                Food.class,
                Pasty.class,
                MysteryMeat.class
        };
        Category.FOOD.probs = new float[]{ 4, 1, 0 };

        Category.RING.classes = new Class<?>[]{
                RingOfMending.class,
                RingOfDetection.class,
                RingOfShadows.class,
                RingOfPower.class,
                RingOfHerbalism.class,
                RingOfAccuracy.class,
                RingOfEvasion.class,
                RingOfSatiety.class,
                RingOfHaste.class,
                RingOfElements.class,
                RingOfHaggler.class,
                RingOfThorns.class };
        Category.RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 };

        Category.SEED.classes = new Class<?>[]{
                Firebloom.Seed.class,
                Icecap.Seed.class,
                Sorrowmoss.Seed.class,
                Dreamweed.Seed.class,
                Sungrass.Seed.class,
                Earthroot.Seed.class,
                Fadeleaf.Seed.class,
                Rotberry.Seed.class
        };
        Category.SEED.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 0 };

        Category.MISC.classes = new Class<?>[]{
                Bomb.class,
                Honeypot.class};
        Category.MISC.probs = new float[]{ 2, 1 };
    }

    public static void reset() {
        for (Category cat : Category.values()) {
            categoryProbs.put( cat, cat.prob );
        }
    }

    public static Item random() {
        return random( Random.chances( categoryProbs ) );
    }

    public static Item random( Category cat ) {
        try {

            categoryProbs.put( cat, categoryProbs.get( cat ) / 2 );

            switch (cat) {
                case ARMOR:
                    return randomArmor();
                case WEAPON:
                    return randomWeapon();
                default:
                    return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
            }

        } catch (Exception e) {

            return null;

        }
    }

    public static Item random( Class<? extends Item> cl ) {
        try {

            return ((Item)cl.newInstance()).random();

        } catch (Exception e) {

            return null;

        }
    }

    public static Armor randomArmor() throws Exception {

        int curStr = Hero.STARTING_STR + Dungeon.potionOfStrength;

        Category cat = Category.ARMOR;

        Armor a1 = (Armor)cat.classes[Random.chances( cat.probs )].newInstance();
        Armor a2 = (Armor)cat.classes[Random.chances( cat.probs )].newInstance();

        a1.random();
        a2.random();

        return Math.abs( curStr - a1.STR ) < Math.abs( curStr - a2.STR ) ? a1 : a2;
    }

    public static Weapon randomWeapon() throws Exception {

        int curStr = Hero.STARTING_STR + Dungeon.potionOfStrength;

        Category cat = Category.WEAPON;

        Weapon w1 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();
        Weapon w2 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();

        w1.random();
        w2.random();

        return Math.abs( curStr - w1.STR ) < Math.abs( curStr - w2.STR ) ? w1 : w2;
    }
}
