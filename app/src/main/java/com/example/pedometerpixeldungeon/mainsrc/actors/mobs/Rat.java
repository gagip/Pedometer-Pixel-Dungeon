package com.example.pedometerpixeldungeon.mainsrc.actors.mobs;

import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.sprites.mobsprites.RatSprite;
import com.example.pedometerpixeldungeon.utils.Random;

public class Rat extends Mob {

    {
        name = "marsupial rat";
        spriteClass = RatSprite.class;

        HP = HT = 8;
        defenseSkill = 3;

        maxLvl = 5;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 1, 5 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 8;
    }

    @Override
    public int dr() {
        return 1;
    }

    @Override
    public void die( Object cause ) {
//        Ghost.Quest.processSewersKill( pos );

        super.die( cause );
    }

    @Override
    public String description() {
        return
                "Marsupial rats are aggressive, but rather weak denizens " +
                        "of the sewers. They can be dangerous only in big numbers.";
    }
}
