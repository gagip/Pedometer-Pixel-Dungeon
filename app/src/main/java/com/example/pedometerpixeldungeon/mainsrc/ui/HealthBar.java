package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.noosa.ColorBlock;
import com.example.pedometerpixeldungeon.noosa.ui.Component;

public class HealthBar extends Component {

    private static final int COLOR_BG	= 0xFFCC0000;
    private static final int COLOR_LVL	= 0xFF00EE00;

    private static final int HEIGHT	= 2;

    private ColorBlock hpBg;
    private ColorBlock hpLvl;

    private float level;

    @Override
    protected void createChildren() {
        hpBg = new ColorBlock( 1, 1, COLOR_BG );
        add( hpBg );

        hpLvl = new ColorBlock( 1, 1, COLOR_LVL );
        add( hpLvl );

        height = HEIGHT;
    }

    @Override
    protected void layout() {

        hpBg.x = hpLvl.x = x;
        hpBg.y = hpLvl.y = y;

        hpBg.size( width, HEIGHT );
        hpLvl.size( width * level, HEIGHT );

        height = HEIGHT;
    }

    public void level( float value ) {
        level = value;
        layout();
    }
}
