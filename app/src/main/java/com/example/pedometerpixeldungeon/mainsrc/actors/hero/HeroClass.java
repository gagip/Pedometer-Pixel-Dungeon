package com.example.pedometerpixeldungeon.mainsrc.actors.hero;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.ClothArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.bags.Keyring;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.Food;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfStrength;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.ShortSword;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.missiles.Dart;
import com.example.pedometerpixeldungeon.mainsrc.ui.QuickSlot;
import com.example.pedometerpixeldungeon.utils.Bundle;

public enum  HeroClass {
    WARRIOR( "warrior" ), MAGE( "mage" ), ROGUE( "rogue" ), HUNTRESS( "huntress" );

    private String title;

    private HeroClass( String title ) {
        this.title = title;
    }

    public static final String[] WAR_PERKS = {
            "Warriors start with 11 points of Strength.",
            "Warriors start with a unique short sword. This sword can be later \"reforged\" to upgrade another melee weapon.",
            "Warriors are less proficient with missile weapons.",
            "Any piece of food restores some health when eaten.",
            "Potions of Strength are identified from the beginning.",
    };

    public static final String[] MAG_PERKS = {
            "Mages start with a unique Wand of Magic Missile. This wand can be later \"disenchanted\" to upgrade another wand.",
            "Mages recharge their wands faster.",
            "When eaten, any piece of food restores 1 charge for all wands in the inventory.",
            "Mages can use wands as a melee weapon.",
            "Scrolls of Identify are identified from the beginning."
    };

    public static final String[] ROG_PERKS = {
            "Rogues start with a Ring of Shadows+1.",
            "Rogues identify a type of a ring on equipping it.",
            "Rogues are proficient with light armor, dodging better while wearing one.",
            "Rogues are proficient in detecting hidden doors and traps.",
            "Rogues can go without food longer.",
            "Scrolls of Magic Mapping are identified from the beginning."
    };

    public static final String[] HUN_PERKS = {
            "Huntresses start with 15 points of Health.",
            "Huntresses start with a unique upgradeable boomerang.",
            "Huntresses are proficient with missile weapons and get a damage bonus for excessive strength when using them.",
            "Huntresses gain more health from dewdrops.",
            "Huntresses sense neighbouring monsters even if they are hidden behind obstacles."
    };

    public void initHero( Hero hero ) {

        hero.heroClass = this;

        initCommon( hero );

        switch (this) {
            case WARRIOR:
                initWarrior( hero );
                break;

            case MAGE:
//                initMage( hero );
                break;

            case ROGUE:
//                initRogue( hero );
                break;

            case HUNTRESS:
//                initHuntress( hero );
                break;
        }

//        if (Badges.isUnlocked( masteryBadge() )) {
//            new TomeOfMastery().collect();
//        }

        hero.updateAwareness();
    }


    private static void initCommon( Hero hero ) {
        (hero.belongings.armor = new ClothArmor()).identify();
        new Food().identify().collect();
        new Keyring().collect();
}

//    public Badges.Badge masteryBadge() {
//        switch (this) {
//            case WARRIOR:
//                return Badges.Badge.MASTERY_WARRIOR;
//            case MAGE:
//                return Badges.Badge.MASTERY_MAGE;
//            case ROGUE:
//                return Badges.Badge.MASTERY_ROGUE;
//            case HUNTRESS:
//                return Badges.Badge.MASTERY_HUNTRESS;
//        }
//        return null;
//    }

    private static void initWarrior( Hero hero ) {
        hero.STR = hero.STR + 1;

        (hero.belongings.weapon = new ShortSword()).identify();
        new Dart( 8 ).identify().collect();

        QuickSlot.primaryValue = Dart.class;

        new PotionOfStrength().setKnown();
    }

//    private static void initMage( Hero hero ) {
//        (hero.belongings.weapon = new Knuckles()).identify();
//
//        WandOfMagicMissile wand = new WandOfMagicMissile();
//        wand.identify().collect();
//
//        QuickSlot.primaryValue = wand;
//
//        new ScrollOfIdentify().setKnown();
//    }
//
//    private static void initRogue( Hero hero ) {
//        (hero.belongings.weapon = new Dagger()).identify();
//        (hero.belongings.ring1 = new RingOfShadows()).upgrade().identify();
//        new Dart( 8 ).identify().collect();
//
//        hero.belongings.ring1.activate( hero );
//
//        QuickSlot.primaryValue = Dart.class;
//
//        new ScrollOfMagicMapping().setKnown();
//    }
//
//    private static void initHuntress( Hero hero ) {
//
//        hero.HP = (hero.HT -= 5);
//
//        (hero.belongings.weapon = new Dagger()).identify();
//        Boomerang boomerang = new Boomerang();
//        boomerang.identify().collect();
//
//        QuickSlot.primaryValue = boomerang;
//    
//    }

    public String title() {
        return title;
    }

    public String spritesheet() {

        switch (this) {
            case WARRIOR:
                return Assets.WARRIOR; // 플레이어 사진 세팅 부분
            case MAGE:
                return Assets.MAGE;
            case ROGUE:
                return Assets.ROGUE;
            case HUNTRESS:
                return Assets.HUNTRESS;
        }

        return null;
    }

    public String[] perks() {

        switch (this) {
            case WARRIOR:
                return WAR_PERKS;
            case MAGE:
                return MAG_PERKS;
            case ROGUE:
                return ROG_PERKS;
            case HUNTRESS:
                return HUN_PERKS;
        }

        return null;
    }

    private static final String CLASS	= "class";

    public void storeInBundle( Bundle bundle ) {
        bundle.put( CLASS, toString() );
    }

    public static HeroClass restoreInBundle( Bundle bundle ) {
        String value = bundle.getString( CLASS );
        return value.length() > 0 ? valueOf( value ) : ROGUE;
    }
}
