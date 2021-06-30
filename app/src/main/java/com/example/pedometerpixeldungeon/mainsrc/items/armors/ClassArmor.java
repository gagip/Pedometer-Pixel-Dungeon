package com.example.pedometerpixeldungeon.mainsrc.items.armors;

import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.utils.Bundle;

import java.util.ArrayList;

abstract public class ClassArmor extends Armor {

    private static final String TXT_LOW_HEALTH		= "Your health is too low!";
    private static final String TXT_NOT_EQUIPPED	= "You need to be wearing this armor to use its special power!";

    private int DR;

    {
        levelKnown = true;
        cursedKnown = true;
        defaultAction = special();
    }

    public ClassArmor() {
        super( 6 );
    }

    public static ClassArmor upgrade (Hero owner, Armor armor ) {

        ClassArmor classArmor = null;

        switch (owner.heroClass) {
            case WARRIOR:
                classArmor = new WarriorArmor();
                break;
            case ROGUE:
//                classArmor = new RogueArmor();
                break;
            case MAGE:
//                classArmor = new MageArmor();
                break;
            case HUNTRESS:
//                classArmor = new HuntressArmor();
                break;
        }

        classArmor.STR = armor.STR;
        classArmor.DR = armor.DR();

        classArmor.inscribe( armor.glyph );

        return classArmor;
    }

    private static final String ARMOR_STR	= "STR";
    private static final String ARMOR_DR	= "DR";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( ARMOR_STR, STR );
        bundle.put( ARMOR_DR, DR );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        STR = bundle.getInt( ARMOR_STR );
        DR = bundle.getInt( ARMOR_DR );
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if (hero.HP >= 3 && isEquipped( hero )) {
            actions.add( special() );
        }
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        if (action == special()) {

            if (hero.HP < 3) {
                GLog.w( TXT_LOW_HEALTH );
            } else if (!isEquipped( hero )) {
                GLog.w( TXT_NOT_EQUIPPED );
            } else {
                curUser = hero;
                doSpecial();
            }

        } else {
            super.execute( hero, action );
        }
    }

    abstract public String special();
    abstract public void doSpecial();

    @Override
    public int DR() {
        return DR;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int price() {
        return 0;
    }

    @Override
    public String desc() {
        return "The thing looks awesome!";
    }
}