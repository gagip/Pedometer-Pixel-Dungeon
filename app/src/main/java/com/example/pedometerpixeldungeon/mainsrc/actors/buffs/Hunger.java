package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

//import com.watabou.pixeldungeon.Badges;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.ResultDescriptions;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.HeroClass;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.utils.Bundle;

//import com.watabou.pixeldungeon.items.rings.RingOfSatiety;


public class Hunger extends Buff implements Hero.Doom {

    private static final float STEP	= 10f;

    public static final float HUNGRY	= 260f;
    public static final float STARVING	= 360f;

    private static final String TXT_HUNGRY		= "You are hungry.";
    private static final String TXT_STARVING	= "You are starving!";
    private static final String TXT_DEATH		= "You starved to death...";

    private float level;

    private static final String LEVEL	= "level";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( LEVEL, level );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        level = bundle.getFloat( LEVEL );
    }

    @Override
    public boolean act() {
        if (target.isAlive()) {

            Hero hero = (Hero)target;

//            if (isStarving()) {
//                if (Random.Float() < 0.3f && (target.HP > 1 || !target.paralysed)) {
//
//                    GLog.n( TXT_STARVING );
//                    hero.damage( 1, this );
//
//                    hero.interrupt();
//                }
//            } else {
//
//                int bonus = 0;
//                for (Buff buff : target.buffs( RingOfSatiety.Satiety.class )) {
//                    bonus += ((RingOfSatiety.Satiety)buff).level;
//                }
//
//                float newLevel = level + STEP - bonus;
//                boolean statusUpdated = false;
//                if (newLevel >= STARVING) {
//
//                    GLog.n( TXT_STARVING );
//                    statusUpdated = true;
//
//                    hero.interrupt();
//
//                } else if (newLevel >= HUNGRY && level < HUNGRY) {
//
//                    GLog.w( TXT_HUNGRY );
//                    statusUpdated = true;
//
//                }
//                level = newLevel;
//
//                if (statusUpdated) {
//                    BuffIndicator.refreshHero();
//                }
//
//            }

            float step = ((Hero)target).heroClass == HeroClass.ROGUE ? STEP * 1.2f : STEP;
//            spend( target.buff( Shadows.class ) == null ? step : step * 1.5f );

        } else {

            diactivate();

        }

        return true;
    }

    public void satisfy( float energy ) {
        level -= energy;
        if (level < 0) {
            level = 0;
        } else if (level > STARVING) {
            level = STARVING;
        }

        BuffIndicator.refreshHero();
    }

    public boolean isStarving() {
        return level >= STARVING;
    }

    @Override
    public int icon() {
        if (level < HUNGRY) {
            return BuffIndicator.NONE;
        } else if (level < STARVING) {
            return BuffIndicator.HUNGER;
        } else {
            return BuffIndicator.STARVATION;
        }
    }

    @Override
    public String toString() {
        if (level < STARVING) {
            return "Hungry";
        } else {
            return "Starving";
        }
    }

    @Override
    public void onDeath() {

//        Badges.validateDeathFromHunger();

        Dungeon.fail( Utils.format( ResultDescriptions.HUNGER, Dungeon.depth ) );
        GLog.n( TXT_DEATH );
    }
}