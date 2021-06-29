package com.example.pedometerpixeldungeon.mainsrc.sprites;

import com.example.pedometerpixeldungeon.noosa.Game;

public class DiscardedItemSprite extends ItemSprite {

    public DiscardedItemSprite() {

        super();

        originToCenter();
        angularSpeed = 720;
    }

    @Override
    public void drop() {
        scale.set( 1 );
        am = 1;
    }

    @Override
    public void update() {

        super.update();

        scale.set( scale.x * 0.9f );
        if ((am -= Game.elapsed) <= 0) {
            remove();
        }
    }
}
