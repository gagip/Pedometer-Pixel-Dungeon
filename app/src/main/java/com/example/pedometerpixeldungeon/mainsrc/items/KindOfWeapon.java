package com.example.pedometerpixeldungeon.mainsrc.items;

import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.ui.QuickSlot;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.ArrayList;

abstract public class KindOfWeapon extends EquipableItem {

    private static final String TXT_EQUIP_CURSED	= "you wince as your grip involuntarily tightens around your %s";

    protected static final float TIME_TO_EQUIP = 1f;

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( isEquipped( hero ) ? AC_UNEQUIP : AC_EQUIP );
        return actions;
    }

    @Override
    public boolean isEquipped( Hero hero ) {
        return hero.belongings.weapon == this;
    }

    @Override
    public boolean doEquip( Hero hero ) {

        detachAll( hero.belongings.backpack );

        if (hero.belongings.weapon == null || hero.belongings.weapon.doUnequip( hero, true )) {

            hero.belongings.weapon = this;
            activate( hero );

            QuickSlot.refresh();

            cursedKnown = true;
            if (cursed) {
                equipCursed( hero );
                GLog.n( TXT_EQUIP_CURSED, name() );
            }

            hero.spendAndNext( TIME_TO_EQUIP );
            return true;

        } else {

            collect( hero.belongings.backpack );
            return false;
        }
    }

    @Override
    public boolean doUnequip(Hero hero, boolean collect, boolean single ) {
        if (super.doUnequip( hero, collect, single )) {

            hero.belongings.weapon = null;
            return true;

        } else {

            return false;

        }
    }

    public void activate( Hero hero ) {
    }

    abstract public int min();
    abstract public int max();

    public int damageRoll( Hero owner ) {
        return Random.NormalIntRange( min(), max() );
    }

    public float acuracyFactor( Hero hero ) {
        return 1f;
    }

    public float speedFactor( Hero hero ) {
        return 1f;
    }

    public void proc(Char attacker, Char defender, int damage ) {
    }

}