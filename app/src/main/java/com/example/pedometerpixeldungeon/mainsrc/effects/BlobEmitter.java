package com.example.pedometerpixeldungeon.mainsrc.effects;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Blob;
import com.example.pedometerpixeldungeon.noosa.particles.Emitter;
import com.example.pedometerpixeldungeon.utils.Random;

public class BlobEmitter extends Emitter {

    private static final int WIDTH	= Blob.WIDTH;
    private static final int LENGTH	= Blob.LENGTH;

    private Blob blob;

    public BlobEmitter( Blob blob ) {

        super();

        this.blob = blob;
        blob.use( this );
    }

    @Override
    protected void emit( int index ) {

        if (blob.volume <= 0) {
            return;
        }

        int[] map = blob.cur;
        float size = DungeonTilemap.SIZE;

        for (int i=0; i < LENGTH; i++) {
            if (map[i] > 0 && Dungeon.visible[i]) {
                float x = ((i % WIDTH) + Random.Float()) * size;
                float y = ((i / WIDTH) + Random.Float()) * size;
                factory.emit( this, index, x, y );
            }
        }
    }
}
