package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.PedometerPixelDungeon;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.noosa.BitmapTextMultiline;

public class WndMessage extends Window {

    private static final int WIDTH_P = 120;
    private static final int WIDTH_L = 144;

    private static final int MARGIN = 4;

    public WndMessage( String text ) {

        super();

        BitmapTextMultiline info = PixelScene.createMultiline( text, 6 );
        info.maxWidth = (PedometerPixelDungeon.landscape() ? WIDTH_L : WIDTH_P) - MARGIN * 2;
        info.measure();
        info.x = info.y = MARGIN;
        add( info );

        resize(
                (int)info.width() + MARGIN * 2,
                (int)info.height() + MARGIN * 2 );
    }
}