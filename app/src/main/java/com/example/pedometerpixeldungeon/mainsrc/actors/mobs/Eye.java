package com.example.pedometerpixeldungeon.mainsrc.actors.mobs;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.ResultDescriptions;
import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Light;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Terror;
import com.example.pedometerpixeldungeon.mainsrc.effects.CellEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.PurpleParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.Dewdrop;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.WandOfDisintegration;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.enchantments.Death;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.enchantments.Leech;
import com.example.pedometerpixeldungeon.mainsrc.mechanics.Ballistica;
import com.example.pedometerpixeldungeon.mainsrc.sprites.CharSprite;
import com.example.pedometerpixeldungeon.mainsrc.sprites.mobsprites.EyeSprite;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.HashSet;

public class Eye extends Mob {
    private static final String TXT_DEATHGAZE_KILLED = "%s's deathgaze killed you...";

    {
        name = "evil eye";
        spriteClass = EyeSprite.class;

        HP = HT = 100;
        defenseSkill = 20;
        viewDistance = Light.DISTANCE;

        EXP = 13;
        maxLvl = 25;

        flying = true;

        loot = new Dewdrop();
        lootChance = 0.5f;
    }

    @Override
    public int dr() {
        return 10;
    }

    private int hitCell;

    @Override
    protected boolean canAttack( Char enemy ) {

        hitCell = Ballistica.cast( pos, enemy.pos, true, false );

        for (int i=1; i < Ballistica.distance; i++) {
            if (Ballistica.trace[i] == enemy.pos) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    protected float attackDelay() {
        return 1.6f;
    }

    @Override
    protected boolean doAttack( Char enemy ) {

        spend( attackDelay() );

        boolean rayVisible = false;

        for (int i=0; i < Ballistica.distance; i++) {
            if (Dungeon.visible[Ballistica.trace[i]]) {
                rayVisible = true;
            }
        }

        if (rayVisible) {
            sprite.attack( hitCell );
            return false;
        } else {
            attack( enemy );
            return true;
        }
    }

    @Override
    public boolean attack( Char enemy ) {

        for (int i=1; i < Ballistica.distance; i++) {

            int pos = Ballistica.trace[i];

            Char ch = Actor.findChar( pos );
            if (ch == null) {
                continue;
            }

            if (hit( this, ch, true )) {
                ch.damage( Random.NormalIntRange( 14, 20 ), this );

                if (Dungeon.visible[pos]) {
                    ch.sprite.flash();
                    CellEmitter.center( pos ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
                }

                if (!ch.isAlive() && ch == Dungeon.hero) {
                    Dungeon.fail( Utils.format( ResultDescriptions.MOB, Utils.indefinite( name ), Dungeon.depth ) );
                    GLog.n( TXT_DEATHGAZE_KILLED, name );
                }
            } else {
                ch.sprite.showStatus( CharSprite.NEUTRAL,  ch.defenseVerb() );
            }
        }

        return true;
    }

    @Override
    public String description() {
        return
                "One of this demon's other names is \"orb of hatred\", because when it sees an enemy, " +
                        "it uses its deathgaze recklessly, often ignoring its allies and wounding them.";
    }

    private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
    static {
        RESISTANCES.add( WandOfDisintegration.class );
        RESISTANCES.add( Death.class );
        RESISTANCES.add( Leech.class );
    }

    @Override
    public HashSet<Class<?>> resistances() {
        return RESISTANCES;
    }

    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
    static {
        IMMUNITIES.add( Terror.class );
    }

    @Override
    public HashSet<Class<?>> immunities() {
        return IMMUNITIES;
    }
}
