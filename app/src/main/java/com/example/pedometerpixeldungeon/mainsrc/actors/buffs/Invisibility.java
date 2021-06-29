package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;

public class Invisibility extends FlavourBuff {

    public static final float DURATION	= 15f;

    @Override
    public boolean attachTo( Char target ) {
        if (super.attachTo( target )) {
            target.invisible++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void detach() {
        target.invisible--;
        super.detach();
    }

    @Override
    public int icon() {
        return BuffIndicator.INVISIBLE;
    }

    @Override
    public String toString() {
        return "Invisible";
    }

    public static void dispel() {
        Invisibility buff = Dungeon.hero.buff( Invisibility.class );
        if (buff != null && Dungeon.hero.visibleEnemies() > 0) {
            buff.detach();
        }
    }
}
