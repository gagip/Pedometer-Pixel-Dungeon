package com.example.pedometerpixeldungeon.mainsrc.items;

import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Light;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.FlameParticle;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.noosa.particles.Emitter;

import java.util.ArrayList;

public class Touch extends Item {

    public static final String AC_LIGHT	= "LIGHT";

    public static final float TIME_TO_LIGHT = 1;

    {
        name = "torch";
        image = ItemSpriteSheet.TORCH;

        stackable = true;

        defaultAction = AC_LIGHT;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_LIGHT );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        if (action == AC_LIGHT) {

            hero.spend( TIME_TO_LIGHT );
            hero.busy();

            hero.sprite.operate( hero.pos );

            detach( hero.belongings.backpack );
            Buff.affect( hero, Light.class, Light.DURATION );

            Emitter emitter = hero.sprite.centerEmitter();
            emitter.start( FlameParticle.FACTORY, 0.2f, 3 );

        } else {

            super.execute( hero, action );

        }
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
    public int price() {
        return 10 * quantity;
    }

    @Override
    public String info() {
        return
                "It's an indispensable item in The Demon Halls, which are notorious for their poor ambient lighting.";
    }
}