package com.example.pedometerpixeldungeon.mainsrc.actors.Blobs;

import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Vertigo;
import com.example.pedometerpixeldungeon.mainsrc.effects.BlobEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.Speck;

public class ConfusionGas extends Blob {

    @Override
    protected void evolve() {
        super.evolve();

        Char ch;
        for (int i=0; i < LENGTH; i++) {
            if (cur[i] > 0 && (ch = Actor.findChar( i )) != null) {
                Buff.prolong( ch, Vertigo.class, Vertigo.duration( ch ) );
            }
        }
    }

    @Override
    public void use( BlobEmitter emitter ) {
        super.use( emitter );

        emitter.pour( Speck.factory( Speck.CONFUSION, true ), 0.6f );
    }

    @Override
    public String tileDesc() {
        return "A cloud of confusion gas is swirling here.";
    }
}
