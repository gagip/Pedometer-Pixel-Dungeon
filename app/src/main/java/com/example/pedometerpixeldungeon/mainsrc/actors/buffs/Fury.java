package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;

public class Fury extends Buff {

    public static float LEVEL	= 0.4f;

    @Override
    public boolean act() {
        if (target.HP > target.HT * LEVEL) {
            detach();
        }

        spend( TICK );

        return true;
    }

    @Override
    public int icon() {
        return BuffIndicator.FURY;
    }

    @Override
    public String toString() {
        return "Fury";
    }
}
