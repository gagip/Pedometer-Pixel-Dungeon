package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

import com.example.pedometerpixeldungeon.mainsrc.actors.Char;

import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfElements;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;

public class Paralysis extends FlavourBuff {

    private static final float DURATION	= 10f;

    @Override
    public boolean attachTo( Char target ) {
        if (super.attachTo( target )) {
            target.paralysed = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void detach() {
        super.detach();
        unfreeze( target );
    }

    @Override
    public int icon() {
        return BuffIndicator.PARALYSIS;
    }

    @Override
    public String toString() {
        return "Paralysed";
    }

    public static float duration( Char ch ) {
        RingOfElements.Resistance r = ch.buff( RingOfElements.Resistance.class );
        return r != null ? r.durationFactor() * DURATION : DURATION;
    }

    public static void unfreeze( Char ch ) {
        if (ch.buff( Paralysis.class ) == null &&
                ch.buff( Frost.class ) == null) {

            ch.paralysed = false;
        }
    }
}