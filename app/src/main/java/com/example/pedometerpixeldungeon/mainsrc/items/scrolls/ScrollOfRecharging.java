package com.example.pedometerpixeldungeon.mainsrc.items.scrolls;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Invisibility;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.EnergyParticle;
import com.example.pedometerpixeldungeon.mainsrc.sprites.SpellSprite;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;

public class ScrollOfRecharging extends Scroll {

    {
        name = "Scroll of Recharging";
    }

    @Override
    protected void doRead() {

        int count = curUser.belongings.charge( true );
        charge( curUser );

        Sample.INSTANCE.play( Assets.SND_READ );
        Invisibility.dispel();

        if (count > 0) {
            GLog.i( "a surge of energy courses through your pack, recharging your wand" + (count > 1 ? "s" : "") );
            SpellSprite.show( curUser, SpellSprite.CHARGE );
        } else {
            GLog.i( "a surge of energy courses through your pack, but nothing happens" );
        }
        setKnown();

        readAnimation();
    }

    @Override
    public String desc() {
        return
                "The raw magical power bound up in this parchment will, when released, " +
                        "recharge all of the reader's wands to full power.";
    }

    public static void charge( Hero hero ) {
        hero.sprite.centerEmitter().burst( EnergyParticle.FACTORY, 15 );
    }

    @Override
    public int price() {
        return isKnown() ? 40 * quantity : super.price();
    }
}