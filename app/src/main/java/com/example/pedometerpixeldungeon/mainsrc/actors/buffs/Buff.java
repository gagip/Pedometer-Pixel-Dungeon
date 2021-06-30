package com.example.pedometerpixeldungeon.mainsrc.actors.buffs;

import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;

public class Buff extends Actor {

    public Char target;

    public boolean attachTo( Char target ) {

        if (target.immunities().contains( getClass() )) {
            return false;
        }

        this.target = target;
        target.add( this );

        return true;
    }

    public void detach() {
        target.remove( this );
    }

    @Override
    public boolean act() {
        diactivate();
        return true;
    }

    public int icon() {
        return BuffIndicator.NONE;
    }

    public static<T extends Buff> T append( Char target, Class<T> buffClass ) {
        try {
            T buff = buffClass.newInstance();
            buff.attachTo( target );
            return buff;
        } catch (Exception e) {
            return null;
        }
    }

    public static<T extends FlavourBuff> T append( Char target, Class<T> buffClass, float duration ) {
        T buff = append( target, buffClass );
        buff.spend( duration );
        return buff;
    }

    public static<T extends Buff> T affect( Char target, Class<T> buffClass ) {
        T buff = target.buff( buffClass );
        if (buff != null) {
            return buff;
        } else {
            return append( target, buffClass );
        }
    }

    public static<T extends FlavourBuff> T affect( Char target, Class<T> buffClass, float duration ) {
        T buff = affect( target, buffClass );
        buff.spend( duration );
        return buff;
    }

    public static<T extends FlavourBuff> T prolong( Char target, Class<T> buffClass, float duration ) {
        T buff = affect( target, buffClass );
        buff.postpone( duration );
        return buff;
    }

    public static void detach( Buff buff ) {
        if (buff != null) {
            buff.detach();
        }
    }

    public static void detach( Char target, Class<? extends Buff> cl ) {
        detach( target.buff( cl ) );
    }
}
