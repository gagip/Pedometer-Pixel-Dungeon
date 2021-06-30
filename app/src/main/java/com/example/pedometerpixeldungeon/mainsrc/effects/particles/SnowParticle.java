package com.example.pedometerpixeldungeon.mainsrc.effects.particles;

import com.example.pedometerpixeldungeon.noosa.particles.Emitter;
import com.example.pedometerpixeldungeon.noosa.particles.PixelParticle;
import com.example.pedometerpixeldungeon.utils.Random;

public class SnowParticle extends PixelParticle {

    public static final Emitter.Factory FACTORY = new Emitter.Factory() {
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((SnowParticle)emitter.recycle( SnowParticle.class )).reset( x, y );
        }
    };

    public SnowParticle() {
        super();
        speed.set( 0, Random.Float( 5, 8 ) );
        lifespan = 1.2f;
    }

    public void reset( float x, float y ) {
        revive();

        this.x = x;
        this.y = y - speed.y * lifespan;

        left = lifespan;
    }

    @Override
    public void update() {
        super.update();
        float p = left / lifespan;
        am = (p < 0.5f ? p : 1 - p) * 1.5f;
    }
}