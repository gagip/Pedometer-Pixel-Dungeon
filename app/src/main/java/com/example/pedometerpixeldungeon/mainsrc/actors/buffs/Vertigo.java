package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfElements.Resistance;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;

public class Vertigo extends FlavourBuff {

    public static final float DURATION	= 10f;

    @Override
    public int icon() {
        return BuffIndicator.VERTIGO;
    }

    @Override
    public String toString() {
        return "Vertigo";
    }

    public static float duration( Char ch ) {
        Resistance r = ch.buff( Resistance.class );
        return r != null ? r.durationFactor() * DURATION : DURATION;
    }
}
