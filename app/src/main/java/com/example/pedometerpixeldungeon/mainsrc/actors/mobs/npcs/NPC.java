package com.example.pedometerpixeldungeon.mainsrc.actors.mobs.npcs;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.utils.Random;

public abstract class NPC extends Mob {

    {
        HP = HT = 1;
        EXP = 0;

        hostile = false;
        state = PASSIVE;
    }

    protected void throwItem() {
        Heap heap = Dungeon.level.heaps.get( pos );
        if (heap != null) {
            int n;
            do {
                n = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
            } while (!Level.passable[n] && !Level.avoid[n]);
            Dungeon.level.drop( heap.pickUp(), n ).sprite.drop( pos );
        }
    }

    @Override
    public void beckon( int cell ) {
    }

    abstract public void interact();
}