package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Chrome;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.noosa.BitmapText;
import com.example.pedometerpixeldungeon.noosa.NinePatch;
import com.example.pedometerpixeldungeon.noosa.ui.Component;

public class Toast extends Component {

    private static final float MARGIN_HOR	= 2;
    private static final float MARGIN_VER	= 2;

    protected NinePatch bg;
    protected SimpleButton close;
    protected BitmapText text;

    public Toast( String text ) {
        super();
        text( text );

        width = this.text.width() + close.width() + bg.marginHor() + MARGIN_HOR * 3;
        height = Math.max( this.text.height(), close.height() ) + bg.marginVer() + MARGIN_VER * 2;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        bg = Chrome.get( Chrome.Type.TOAST_TR );
        add( bg );

        close = new SimpleButton( Icons.get( Icons.CLOSE ) ) {
            protected void onClick() {
                onClose();
            };
        };
        add( close );

        text = PixelScene.createText( 8 );
        add( text );
    }

    @Override
    protected void layout() {
        super.layout();

        bg.x = x;
        bg.y = y;
        bg.size( width, height );

        close.setPos(
                bg.x + bg.width() - bg.marginHor() / 2 - MARGIN_HOR - close.width(),
                y + (height - close.height()) / 2 );

        text.x = close.left() - MARGIN_HOR - text.width();
        text.y = y + (height - text.height()) / 2;
        PixelScene.align( text );
    }

    public void text( String txt ) {
        text.text( txt );
        text.measure();
    }

    protected void onClose() {};
}
