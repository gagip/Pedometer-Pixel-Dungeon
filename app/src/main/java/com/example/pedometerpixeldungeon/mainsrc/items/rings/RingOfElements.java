package com.example.pedometerpixeldungeon.mainsrc.items.rings;

import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.ToxicGas;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Burning;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Poison;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Eye;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Warlock;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Yog;
import com.example.pedometerpixeldungeon.mainsrc.levels.traps.LightningTrap;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.HashSet;

public class RingOfElements extends Ring {

    {
        name = "Ring of Elements";
    }

    @Override
    protected RingBuff buff( ) {
        return new Resistance();
    }

    @Override
    public String desc() {
        return isKnown() ?
                "This ring provides resistance to different elements, such as fire, " +
                        "electricity, gases etc. Also it decreases duration of negative effects." :
                super.desc();
    }

    private static final HashSet<Class<?>> EMPTY = new HashSet<Class<?>>();
    private static final HashSet<Class<?>> FULL;
    static {
        FULL = new HashSet<Class<?>>();
        FULL.add( Burning.class );
        FULL.add( ToxicGas.class );
        FULL.add( Poison.class );
        FULL.add( LightningTrap.Electricity.class );
        FULL.add( Warlock.class );
        FULL.add( Eye.class );
        FULL.add( Yog.BurningFist.class );
    }

    public class Resistance extends RingBuff {

        public HashSet<Class<?>> resistances() {
            if (Random.Int( level + 3 ) >= 3) {
                return FULL;
            } else {
                return EMPTY;
            }
        }

        public float durationFactor() {
            return level < 0 ? 1 : (2 + 0.5f * level) / (2 + level);
        }
    }
}
