package com.example.pedometerpixeldungeon.mainsrc.effects;

import com.example.pedometerpixeldungeon.gltextures.SmartTexture;
import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.noosa.NinePatch;

public class ShadowBox extends NinePatch {

    public static final float SIZE	= 16;

    public ShadowBox() {
        super( Assets.SHADOW, 1 );

        texture.filter( SmartTexture.LINEAR, SmartTexture.LINEAR );

        scale.set( SIZE, SIZE );
    }

    @Override
    public void size(float width, float height) {
        super.size( width / SIZE, height / SIZE );
    }

    public void boxRect( float x, float y, float width, float height ) {
        this.x = x - SIZE;
        this.y = y - SIZE;
        size( width + SIZE * 2, height + SIZE * 2 );
    }
}
