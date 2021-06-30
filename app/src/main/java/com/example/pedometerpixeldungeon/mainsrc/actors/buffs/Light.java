package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;

public class Light extends FlavourBuff {

    public static final float DURATION	= 250f;
    public static final int DISTANCE	= 4;

    @Override
    public boolean attachTo( Char target ) {
        if (super.attachTo( target )) {
            if (Dungeon.level != null) {
                target.viewDistance = Math.max( Dungeon.level.viewDistance, DISTANCE );
                Dungeon.observe();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void detach() {
        target.viewDistance = Dungeon.level.viewDistance;
        Dungeon.observe();
        super.detach();
    }

    @Override
    public int icon() {
        return BuffIndicator.LIGHT;
    }

    @Override
    public String toString() {
        return "Illuminated";
    }
}
