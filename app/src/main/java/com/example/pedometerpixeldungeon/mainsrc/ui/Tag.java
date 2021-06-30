package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Chrome;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.NinePatch;
import com.example.pedometerpixeldungeon.noosa.ui.Button;

public class Tag extends Button {

    private float r;
    private float g;
    private float b;
    protected NinePatch bg;

    protected float lightness = 0;

    public Tag( int color ) {
        super();

        this.r = (color >> 16) / 255f;
        this.g = ((color >> 8) & 0xFF) / 255f;
        this.b = (color & 0xFF) / 255f;
    }

    @Override
    protected void createChildren() {

        super.createChildren();

        bg = Chrome.get( Chrome.Type.TAG );
        add( bg );
    }

    @Override
    protected void layout() {

        super.layout();

        bg.x = x;
        bg.y = y;
        bg.size( width, height );
    }

    public void flash() {
        lightness = 1f;
    }

    @Override
    public void update() {
        super.update();

        if (visible && lightness > 0.5) {
            if ((lightness -= Game.elapsed) > 0.5) {
                bg.ra = bg.ga = bg.ba = 2 * lightness - 1;
                bg.rm = 2 * r * (1 - lightness);
                bg.gm = 2 * g * (1 - lightness);
                bg.bm = 2 * b * (1 - lightness);
            } else {
                bg.hardlight( r, g, b );
            }
        }
    }
}