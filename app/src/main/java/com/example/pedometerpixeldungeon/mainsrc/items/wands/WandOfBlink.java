package com.example.pedometerpixeldungeon.mainsrc.items.wands;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.effects.MagicMissile;
import com.example.pedometerpixeldungeon.mainsrc.effects.Speck;
import com.example.pedometerpixeldungeon.mainsrc.mechanics.Ballistica;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.noosa.tweeners.AlphaTweener;
import com.example.pedometerpixeldungeon.utils.Callback;

public class WandOfBlink extends Wand {

    {
        name = "Wand of Blink";
    }

    @Override
    protected void onZap( int cell ) {

        int level = power();

        if (Ballistica.distance > level + 4) {
            cell = Ballistica.trace[level + 3];
        } else if (Actor.findChar( cell ) != null && Ballistica.distance > 1) {
            cell = Ballistica.trace[Ballistica.distance - 2];
        }

        curUser.sprite.visible = true;
        appear( Dungeon.hero, cell );
        Dungeon.observe();
    }

    @Override
    protected void fx( int cell, Callback callback ) {
        MagicMissile.whiteLight( curUser.sprite.parent, curUser.pos, cell, callback );
        Sample.INSTANCE.play( Assets.SND_ZAP );
        curUser.sprite.visible = false;
    }

    public static void appear(Char ch, int pos ) {

        ch.sprite.interruptMotion();

        ch.move( pos );
        ch.sprite.place( pos );

        if (ch.invisible == 0) {
            ch.sprite.alpha( 0 );
            ch.sprite.parent.add( new AlphaTweener( ch.sprite, 1, 0.4f ) );
        }

        ch.sprite.emitter().start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
        Sample.INSTANCE.play( Assets.SND_TELEPORT );
    }

    @Override
    public String desc() {
        return
                "This wand will allow you to teleport in the chosen direction. " +
                        "Creatures and inanimate obstructions will block the teleportation.";
    }
}