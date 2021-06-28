package com.example.pedometerpixeldungeon.mainsrc.items.keys;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.utils.Bundle;

public class Key extends Item {
    public static final float TIME_TO_UNLOCK = 1f;

    {
        stackable = false;
    }

    public int depth;

    public Key() {
        super();
        depth = Dungeon.depth;
    }

    private static final String DEPTH = "depth";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( DEPTH, depth );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        depth = bundle.getInt( DEPTH );
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
    public String status() {
        return depth + "\u007F";
    }
}
