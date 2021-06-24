package com.example.pedometerpixeldungeon.mainsrc.actors.hero;

import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.levels.items.Item;

import java.util.ArrayList;

public class Hero extends Char {
    private static final String TXT_LEAVE = "One does not simply leave Pixel Dungeon.";

    private static final String TXT_LEVEL_UP = "level up!";
    private static final String TXT_NEW_LEVEL =
            "Welcome to level %d! Now you are healthier and more focused. " +
                    "It's easier for you to hit enemies and dodge their attacks.";

    public static final String TXT_YOU_NOW_HAVE	= "You now have %s";

    private static final String TXT_SOMETHING_ELSE	= "There is something else here";
    private static final String TXT_LOCKED_CHEST	= "This chest is locked and you don't have matching key";
    private static final String TXT_LOCKED_DOOR		= "You don't have a matching key";
    private static final String TXT_NOTICED_SMTH	= "You noticed something";

    private static final String TXT_WAIT	= "...";
    private static final String TXT_SEARCH	= "search";

    public static final int STARTING_STR = 10;

    private static final float TIME_TO_REST		= 1f;
    private static final float TIME_TO_SEARCH	= 2f;

    public HeroClass heroClass = HeroClass.ROGUE;
//    public HeroSubClass subClass = HeroSubClass.NONE;

    private int attackSkill = 10;
    private int defenseSkill = 5;


    public boolean ready = false;

//    public HeroAction curAction = null;
//    public HeroAction lastAction = null;

    private Char enemy;

//    public Armor.Glyph killerGlyph = null;

    private Item theKey;

    public boolean restoreHealth = false;

//    public MissileWeapon getRangedWeapon() {
//        return rangedWeapon;
//    }
//
//    rangedWeapon = null;
//    public Belongings belongings;

    public int STR;
    public boolean weakened = false;

    public float awareness;

    public int lvl = 1;
    public int exp = 0;

    private ArrayList<Mob> visibleEnemies;

    public Hero() {
        super();
        name = "you";

        HP = HT = 20;
        STR = STARTING_STR;
        awareness = 0.1f;

//        belongings = new Belongings( this );

        visibleEnemies = new ArrayList<Mob>();
    }

    public int STR() {
        return weakened ? STR - 2 : STR;
    }

    private static final String ATTACK		= "attackSkill";
    private static final String DEFENSE		= "defenseSkill";
    private static final String STRENGTH	= "STR";
    private static final String LEVEL		= "lvl";
    private static final String EXPERIENCE	= "exp";

    public boolean isAlive() {
        return HP > 0;
    }


    public void live() {
//        Buff.affect( this, Regeneration.class );
//        Buff.affect( this, Hunger.class );
    }


    void updateAwareness() {
        awareness = (float)(1 - Math.pow(
                (heroClass == HeroClass.ROGUE ? 0.85 : 0.90),
                (1 + Math.min( lvl,  9 )) * 0.5
        ));
    }
}
