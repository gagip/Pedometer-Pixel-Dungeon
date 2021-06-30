package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

public class FlavourBuff extends Buff {

    @Override
    public boolean act() {
        detach();
        return true;
    }
}