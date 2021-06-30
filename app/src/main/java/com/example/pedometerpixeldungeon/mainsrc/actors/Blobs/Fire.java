package com.example.pedometerpixeldungeon.mainsrc.actors.Blobs;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Burning;
import com.example.pedometerpixeldungeon.mainsrc.effects.BlobEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.FlameParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;

public class Fire extends Blob {

    @Override
    protected void evolve() {

        boolean[] flamable = Level.flamable;

        int from = WIDTH + 1;
        int to = Level.LENGTH - WIDTH - 1;

        boolean observe = false;

        for (int pos=from; pos < to; pos++) {

            int fire;

            if (cur[pos] > 0) {

                burn( pos );

                fire = cur[pos] - 1;
                if (fire <= 0 && flamable[pos]) {

                    int oldTile = Dungeon.level.map[pos];
                    Dungeon.level.destroy( pos );

                    observe = true;
                    GameScene.updateMap( pos );
                    if (Dungeon.visible[pos]) {
                        GameScene.discoverTile( pos, oldTile );
                    }
                }

            } else {

                if (flamable[pos] && (cur[pos-1] > 0 || cur[pos+1] > 0 || cur[pos-WIDTH] > 0 || cur[pos+WIDTH] > 0)) {
                    fire = 4;
                    burn( pos );
                } else {
                    fire = 0;
                }

            }

            volume += (off[pos] = fire);

        }

        if (observe) {
            Dungeon.observe();
        }
    }

    private void burn( int pos ) {
        Char ch = Actor.findChar( pos );
        if (ch != null) {
            Buff.affect( ch, Burning.class ).reignite( ch );
        }

        Heap heap = Dungeon.level.heaps.get( pos );
        if (heap != null) {
            heap.burn();
        }
    }

    public void seed( int cell, int amount ) {
        if (cur[cell] == 0) {
            volume += amount;
            cur[cell] = amount;
        }
    }

    @Override
    public void use( BlobEmitter emitter ) {
        super.use( emitter );
        emitter.start( FlameParticle.FACTORY, 0.03f, 0 );
    }

    @Override
    public String tileDesc() {
        return "A fire is raging here.";
    }
}