package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.ResultDescriptions;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Blob;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Fire;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Thief;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.ElmoParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.ChargrilledMeat;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.MysteryMeat;
import com.example.pedometerpixeldungeon.mainsrc.items.rings.RingOfElements.Resistance;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.Scroll;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.utils.Bundle;
import com.example.pedometerpixeldungeon.utils.Random;

public class Burning extends Buff implements Hero.Doom {

    private static final String TXT_BURNS_UP		= "%s burns up!";
    private static final String TXT_BURNED_TO_DEATH	= "You burned to death...";

    private static final float DURATION = 8f;

    private float left;

    private static final String LEFT	= "left";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( LEFT, left );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        left = bundle.getFloat( LEFT );
    }

    @Override
    public boolean act() {

        if (target.isAlive()) {

            if (target instanceof Hero) {
                Buff.prolong( target, Light.class, TICK * 1.01f );
            }

            target.damage( Random.Int( 1, 5 ), this );

            if (target instanceof Hero) {

                Item item = ((Hero)target).belongings.randomUnequipped();
                if (item instanceof Scroll) {

                    item = item.detach( ((Hero)target).belongings.backpack );
                    GLog.w( TXT_BURNS_UP, item.toString() );

                    Heap.burnFX( target.pos );

                }
                else if (item instanceof MysteryMeat) {

                    item = item.detach( ((Hero)target).belongings.backpack );
                    ChargrilledMeat steak = new ChargrilledMeat();
                    if (!steak.collect( ((Hero)target).belongings.backpack )) {
                        Dungeon.level.drop( steak, target.pos ).sprite.drop();
                    }
                    GLog.w( TXT_BURNS_UP, item.toString() );

                    Heap.burnFX( target.pos );

                }

            }
            else if (target instanceof Thief && ((Thief)target).item instanceof Scroll) {

                ((Thief)target).item = null;
                target.sprite.emitter().burst( ElmoParticle.FACTORY, 6 );
            }

        } else {
            detach();
        }

        if (Level.flamable[target.pos]) {
            GameScene.add( Blob.seed( target.pos, 4, Fire.class ) );
        }

        spend( TICK );
        left -= TICK;

        if (left <= 0 ||
                Random.Float() > (2 + (float)target.HP / target.HT) / 3 ||
                (Level.water[target.pos] && !target.flying)) {

            detach();
        }

        return true;
    }

    public void reignite( Char ch ) {
        left = duration( ch );
    }

    @Override
    public int icon() {
        return BuffIndicator.FIRE;
    }

    @Override
    public String toString() {
        return "Burning";
    }

    public static float duration( Char ch ) {
        Resistance r = ch.buff( Resistance.class );
        return r != null ? r.durationFactor() * DURATION : DURATION;
    }

    @Override
    public void onDeath() {

//        Badges.validateDeathFromFire();

        Dungeon.fail( Utils.format( ResultDescriptions.BURNING, Dungeon.depth ) );
        GLog.n( TXT_BURNED_TO_DEATH );
    }
}

