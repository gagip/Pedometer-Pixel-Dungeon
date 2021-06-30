package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfMending;

public class Regeneration extends Buff {

    private static final float REGENERATION_DELAY = 10;

    @Override
    public boolean act() {
        if (target.isAlive()) {

            if (target.HP < target.HT && !((Hero)target).isStarving()) {
                target.HP += 1;
            }

            int bonus = 0;
            for (Buff buff : target.buffs( RingOfMending.Rejuvenation.class )) {
                bonus += ((RingOfMending.Rejuvenation)buff).level;
            }

            spend( (float)(REGENERATION_DELAY / Math.pow( 1.2, bonus )) );

        } else {

            diactivate();

        }

        return true;
    }
}