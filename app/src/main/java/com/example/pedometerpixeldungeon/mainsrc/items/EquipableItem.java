package com.example.pedometerpixeldungeon.mainsrc.items;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.ShadowParticle;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;

public abstract class EquipableItem extends Item {

    private static final String TXT_UNEQUIP_CURSED	= "You can't remove cursed %s!";

    public static final String AC_EQUIP		= "EQUIP";
    public static final String AC_UNEQUIP	= "UNEQUIP";

    @Override
    public void execute( Hero hero, String action ) {
        if (action.equals( AC_EQUIP )) {
            doEquip( hero );
        } else if (action.equals( AC_UNEQUIP )) {
            doUnequip( hero, true );
        } else {
            super.execute( hero, action );
        }
    }

    @Override
    public void doDrop( Hero hero ) {
        if (!isEquipped( hero ) || doUnequip( hero, false, false )) {
            super.doDrop( hero );
        }
    }

    @Override
    public void cast( final Hero user, int dst ) {

        if (isEquipped( user )) {
            if (quantity == 1 && !this.doUnequip( user, false, false )) {
                return;
            }
        }

        super.cast( user, dst );
    }

    protected static void equipCursed( Hero hero ) {
        hero.sprite.emitter().burst( ShadowParticle.CURSE, 6 );
        Sample.INSTANCE.play( Assets.SND_CURSED );
    }

    protected float time2equip( Hero hero ) {
        return 1;
    }

    public abstract boolean doEquip( Hero hero );

    public boolean doUnequip( Hero hero, boolean collect, boolean single ) {

        if (cursed) {
            GLog.w( TXT_UNEQUIP_CURSED, name() );
            return false;
        }

        if (single) {
            hero.spendAndNext( time2equip( hero ) );
        } else {
            hero.spend( time2equip( hero ) );
        }

        if (collect && !collect( hero.belongings.backpack )) {
            Dungeon.level.drop( this, hero.pos );
        }

        return true;
    }

    public final boolean doUnequip( Hero hero, boolean collect ) {
        return doUnequip( hero, collect, true );
    }
}